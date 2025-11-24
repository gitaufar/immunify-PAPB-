# Quick Reference: Child & Appointment Firebase Integration

## Struktur Firestore (New)

```
users/
  {userId}/                    ← Firebase Auth UID
    appointments/              ← Subcollection
      {appointmentId}/
    children/                  ← Subcollection
      {childId}/
```

## Add Child Profile

### 1. Tampilkan Bottom Sheet
```kotlin
var showAddProfile by remember { mutableStateOf(false) }

if (showAddProfile) {
    AddProfileSheet(
        onDismiss = { showAddProfile = false },
        onSuccess = {
            // Refresh data atau navigate
        }
    )
}
```

### 2. Form otomatis handle:
- ✅ Firebase Auth check (userId dari currentUser.uid)
- ✅ Validation (name, birthDate, gender required)
- ✅ Loading state dengan CircularProgressIndicator
- ✅ Success/Error Toast messages
- ✅ Auto-dismiss setelah success
- ✅ Save ke `users/{userId}/children/{childId}`

## Get Children List

```kotlin
val childViewModel: ChildViewModel = hiltViewModel()
val authViewModel: AuthViewModel = hiltViewModel()
val userChildrenState by childViewModel.userChildrenState.collectAsState()

val currentUser = authViewModel.getUser()
val userId = currentUser?.uid

LaunchedEffect(userId) {
    userId?.let { childViewModel.getUserChildren(it) }
}

when (userChildrenState) {
    is ChildUiState.Loading -> CircularProgressIndicator()
    is ChildUiState.ChildrenLoaded -> {
        val children = (userChildrenState as ChildUiState.ChildrenLoaded).children
        // Display children
    }
    is ChildUiState.Error -> {
        Text((userChildrenState as ChildUiState.Error).message)
    }
    else -> {}
}
```

## Create Appointment (Updated Path)

```kotlin
val appointment = Appointment(
    userId = firebaseAuth.currentUser?.uid ?: "",
    userName = "User Name",
    clinicId = "clinic_123",
    // ... other fields
)

appointmentViewModel.createAppointment(appointment)
// Saves to: users/{userId}/appointments/{appointmentId}
```

## Key Classes

| Class | Purpose | Path |
|-------|---------|------|
| `Child` | Domain model | `domain/model/Child.kt` |
| `ChildRepository` | Interface | `domain/repo/ChildRepository.kt` |
| `ChildRepositoryImpl` | Implementation | `data/repo/ChildRepositoryImpl.kt` |
| `CreateChildUseCase` | Validation + Create | `domain/usecase/CreateChildUseCase.kt` |
| `GetUserChildrenUseCase` | Fetch children | `domain/usecase/GetUserChildrenUseCase.kt` |
| `ChildViewModel` | State management | `ui/profile/viewmodel/ChildViewModel.kt` |
| `ChildUiState` | UI states | `ui/profile/viewmodel/ChildUiState.kt` |
| `AddProfileSheet` | UI component | `ui/component/AddProfileSheet.kt` |

## ViewModel Methods

### ChildViewModel
```kotlin
fun createChild(child: Child)              // Create new child
fun getUserChildren(userId: String)        // Get children list
fun resetCreateState()                     // Reset create state to Idle
fun resetChildrenState()                   // Reset children state to Idle
```

### StateFlows
```kotlin
val createChildState: StateFlow<ChildUiState>    // Create operation state
val userChildrenState: StateFlow<ChildUiState>   // Children list state
```

## UI States

```kotlin
sealed class ChildUiState {
    object Idle                                    // Initial state
    object Loading                                 // Operation in progress
    data class Success(val message: String)        // Success with message
    data class Error(val message: String)          // Error with message
    data class ChildrenLoaded(val children: List<Child>)  // Data loaded
}
```

## Validation Rules (Auto-handled by UseCase)

- ✅ userId must not be empty
- ✅ name must not be empty
- ✅ birthDate must not be empty (format: yyyy-MM-dd)
- ✅ gender must not be empty ("Male" or "Female")

## Firebase Security Rules (Deploy ini!)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
      
      match /appointments/{appointmentId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
      }
      
      match /children/{childId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
      }
    }
  }
}
```

Deploy via: **Firebase Console → Firestore Database → Rules → Publish**

## Firestore Indexes (Optional, untuk performa)

Create di Firebase Console → Firestore Database → Indexes:

1. **children collection:**
   - Field: `createdAt` | Type: Ascending
   - Field: `name` | Type: Ascending

2. **appointments collection:**
   - Field: `createdAt` | Type: Descending
   - Field: `appointmentDate` | Type: Ascending
   - Field: `status` | Type: Ascending

## Common Issues & Solutions

### ❌ "User not authenticated"
```kotlin
// Solution: Check if user logged in
val userId = authViewModel.getUser()?.uid
if (userId == null) {
    // Redirect to login or show error
}
```

### ❌ Children list empty tapi ada data
```kotlin
// Check userId nya sama dengan yang di Firestore
Log.d("DEBUG", "UserId: ${authViewModel.getUser()?.uid}")

// Verify path di Firestore Console:
// users/{userId}/children/{childId}
```

### ❌ Permission denied error
```kotlin
// Deploy Firebase Security Rules (lihat section di atas)
// Verify user authenticated sebelum query
```

## Testing Steps

1. **Login dengan Firebase Auth**
2. **Open AddProfileSheet**
3. **Fill form:**
   - Child Name: "John Doe"
   - Birth Date: "15 / 05 / 2020"
   - Gender: Male
4. **Click Add**
5. **Verify di Firestore Console:**
   - Path: `users/{your-uid}/children/{auto-id}`
   - Fields: name, birthDate, gender, timestamps
6. **Test Get Children:**
   - Call `getUserChildren(userId)`
   - Verify list muncul di UI

## Migration Notes

⚠️ **Path lama tidak akan terbaca!**

| Old Path | New Path |
|----------|----------|
| `appointments/{userId}/userAppointments/{id}` | `users/{userId}/appointments/{id}` |

Jika ada data existing, perlu migration script atau read dari kedua path.

## Dokumentasi Lengkap

- **Struktur detail:** `FIRESTORE_STRUCTURE.md`
- **Integration summary:** `ADDPROFILE_INTEGRATION_SUMMARY.md`
- **Firestore UID fix:** `FIREBASE_UID_FIX.md`
