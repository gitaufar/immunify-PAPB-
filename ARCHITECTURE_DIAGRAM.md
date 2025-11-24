# Arsitektur Clean Architecture - Child & Appointment Module

## Layer Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                          │
│  ┌────────────────────┐         ┌──────────────────────┐       │
│  │  AddProfileSheet   │         │   ProfileScreen      │       │
│  │  (Compose UI)      │         │   TrackerScreen      │       │
│  └─────────┬──────────┘         └──────────┬───────────┘       │
│            │                               │                    │
│            └───────────────┬───────────────┘                    │
│                           │                                     │
│                 ┌─────────▼─────────┐                          │
│                 │  ChildViewModel   │                          │
│                 │  @HiltViewModel   │                          │
│                 │                   │                          │
│                 │ - createChild()   │                          │
│                 │ - getUserChildren()│                         │
│                 └─────────┬─────────┘                          │
└────────────────────────────┼──────────────────────────────────┘
                             │
┌────────────────────────────┼──────────────────────────────────┐
│                      DOMAIN LAYER                               │
│                  ┌─────────▼─────────┐                         │
│                  │    Use Cases      │                         │
│                  │                   │                         │
│     ┌────────────┴────────────┬──────┴──────────┐             │
│     │                         │                  │             │
│     ▼                         ▼                  ▼             │
│ ┌────────────────┐  ┌──────────────────┐  ┌──────────────┐   │
│ │ CreateChild    │  │ GetUserChildren  │  │ UpdateChild  │   │
│ │ UseCase        │  │ UseCase          │  │ UseCase      │   │
│ └───────┬────────┘  └────────┬─────────┘  └──────┬───────┘   │
│         │                    │                    │           │
│         └────────────────────┼────────────────────┘           │
│                             │                                 │
│                  ┌──────────▼──────────┐                      │
│                  │  ChildRepository    │                      │
│                  │  (Interface)        │                      │
│                  └──────────┬──────────┘                      │
│                             │                                 │
│                  ┌──────────▼──────────┐                      │
│                  │    Child Model      │                      │
│                  │                     │                      │
│                  │ - id: String        │                      │
│                  │ - userId: String    │                      │
│                  │ - name: String      │                      │
│                  │ - birthDate: String │                      │
│                  │ - gender: String    │                      │
│                  │ - timestamps        │                      │
│                  └─────────────────────┘                      │
└────────────────────────────┼──────────────────────────────────┘
                             │
┌────────────────────────────┼──────────────────────────────────┐
│                      DATA LAYER                                 │
│                  ┌──────────▼──────────┐                       │
│                  │ ChildRepositoryImpl │                       │
│                  │ @Inject constructor │                       │
│                  └──────────┬──────────┘                       │
│                             │                                  │
│              ┌──────────────┼──────────────┐                   │
│              │              │              │                   │
│              ▼              ▼              ▼                   │
│    ┌──────────────┐  ┌──────────┐  ┌──────────────┐          │
│    │ChildMapper   │  │ChildDto  │  │ Firestore    │          │
│    │              │  │          │  │ Datasource   │          │
│    │toDomain()    │  │          │  │              │          │
│    │toDto()       │  │          │  │ - firestore  │          │
│    └──────────────┘  └──────────┘  └──────┬───────┘          │
└───────────────────────────────────────────┼──────────────────┘
                                            │
                                            ▼
                             ┌──────────────────────────┐
                             │   FIREBASE FIRESTORE     │
                             │                          │
                             │  users/                  │
                             │    {userId}/             │
                             │      children/           │
                             │        {childId}/        │
                             │      appointments/       │
                             │        {appointmentId}/  │
                             └──────────────────────────┘
```

## Data Flow Diagram

### Create Child Flow
```
User Input (AddProfileSheet)
    │
    ├─► Full Name: "John Doe"
    ├─► Birth Date: 15/05/2020
    └─► Gender: Male
    │
    ▼
[Validation in UI]
    │
    ├─► Check all fields filled
    └─► Enable/Disable button
    │
    ▼
[Create Child Model]
    │
    └─► Child(
          userId = firebase.uid,
          name = "John Doe",
          birthDate = "2020-05-15",
          gender = "Male"
        )
    │
    ▼
[ChildViewModel.createChild()]
    │
    └─► State: Loading
    │
    ▼
[CreateChildUseCase]
    │
    ├─► Validate userId not empty
    ├─► Validate name not empty
    ├─► Validate birthDate not empty
    └─► Validate gender not empty
    │
    ▼
[ChildRepository.createChild()]
    │
    ▼
[ChildRepositoryImpl]
    │
    ├─► Convert Child → ChildDto
    ├─► Generate ID if empty
    └─► Save to Firestore
    │
    ▼
[FirebaseFirestoreDatasource]
    │
    └─► firestore
         .collection("users")
         .document(userId)
         .collection("children")
         .document(childId)
         .set(childDto)
    │
    ▼
[Result<String>]
    │
    ├─► Success(childId)
    │   └─► State: Success("Child profile created")
    │       └─► Show Toast
    │           └─► Dismiss Sheet
    │               └─► Refresh List
    │
    └─► Error(exception)
        └─► State: Error("Failed to create")
            └─► Show Toast
```

### Get Children Flow
```
[Screen Launch / User Action]
    │
    ▼
[Get Firebase Auth UID]
    │
    └─► val userId = currentUser?.uid
    │
    ▼
[ChildViewModel.getUserChildren(userId)]
    │
    └─► State: Loading
    │
    ▼
[GetUserChildrenUseCase]
    │
    └─► Validate userId not empty
    │
    ▼
[ChildRepository.getChildrenByUserId()]
    │
    ▼
[ChildRepositoryImpl]
    │
    └─► Query Firestore
    │
    ▼
[FirebaseFirestoreDatasource]
    │
    └─► firestore
         .collection("users")
         .document(userId)
         .collection("children")
         .orderBy("createdAt")
         .get()
    │
    ▼
[List<ChildDto>]
    │
    └─► Convert ChildDto → Child (map)
    │
    ▼
[Result<List<Child>>]
    │
    ├─► Success(children)
    │   └─► State: ChildrenLoaded(children)
    │       └─► Display in UI
    │
    └─► Error(exception)
        └─► State: Error("Failed to load")
            └─► Show Error Message
```

## Dependency Injection Graph

```
┌─────────────────────────────────────┐
│       ChildModule                    │
│       @InstallIn(SingletonComponent) │
└─────────────────┬───────────────────┘
                  │
                  ├─► provideChildRepository()
                  │   │
                  │   ├─ Requires: FirebaseFirestoreDatasource
                  │   └─ Returns: ChildRepository (ChildRepositoryImpl)
                  │
                  ├─► provideCreateChildUseCase()
                  │   │
                  │   ├─ Requires: ChildRepository
                  │   └─ Returns: CreateChildUseCase
                  │
                  └─► provideGetUserChildrenUseCase()
                      │
                      ├─ Requires: ChildRepository
                      └─ Returns: GetUserChildrenUseCase

┌─────────────────────────────────────┐
│       ChildViewModel                 │
│       @HiltViewModel                 │
└─────────────────┬───────────────────┘
                  │
                  ├─ @Inject CreateChildUseCase
                  └─ @Inject GetUserChildrenUseCase

┌─────────────────────────────────────┐
│       AddProfileSheet                │
│       @Composable                    │
└─────────────────┬───────────────────┘
                  │
                  ├─ childViewModel = hiltViewModel()
                  └─ authViewModel = hiltViewModel()
```

## State Management Flow

```
┌────────────────────────────────────────────────────────────┐
│                      ChildViewModel                         │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  MutableStateFlow<ChildUiState>                            │
│       │                                                     │
│       ├─► _createChildState                                │
│       │   │                                                 │
│       │   ├─ Idle       (initial)                          │
│       │   ├─ Loading    (operation in progress)            │
│       │   ├─ Success    (child created)                    │
│       │   └─ Error      (failed to create)                 │
│       │                                                     │
│       └─► _userChildrenState                               │
│           │                                                 │
│           ├─ Idle            (initial)                     │
│           ├─ Loading         (fetching data)               │
│           ├─ ChildrenLoaded  (data received)               │
│           └─ Error           (failed to load)              │
│                                                             │
│  StateFlow<ChildUiState> (exposed to UI)                   │
│       │                                                     │
│       ├─► createChildState   = _createChildState.asStateFlow()
│       └─► userChildrenState  = _userChildrenState.asStateFlow()
│                                                             │
└────────────────────────────────────────────────────────────┘
                             │
                             │ collectAsState()
                             ▼
┌────────────────────────────────────────────────────────────┐
│                    Composable UI                            │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  val createChildState by childViewModel                    │
│                           .createChildState                 │
│                           .collectAsState()                 │
│                                                             │
│  LaunchedEffect(createChildState) {                        │
│    when (createChildState) {                               │
│      is Success -> {                                       │
│        showToast()                                         │
│        dismissSheet()                                      │
│      }                                                      │
│      is Error -> showToast()                               │
│      is Loading -> showProgressBar()                       │
│      else -> {}                                            │
│    }                                                        │
│  }                                                          │
│                                                             │
└────────────────────────────────────────────────────────────┘
```

## Files Structure

```
app/src/main/java/com/example/immunify/
│
├── domain/
│   ├── model/
│   │   └── Child.kt                    ✅ Created
│   ├── repo/
│   │   └── ChildRepository.kt          ✅ Created
│   └── usecase/
│       ├── CreateChildUseCase.kt       ✅ Created
│       └── GetUserChildrenUseCase.kt   ✅ Created
│
├── data/
│   ├── firebase/
│   │   └── dto/
│   │       └── ChildDto.kt             ✅ Created
│   ├── mapper/
│   │   └── ChildMapper.kt              ✅ Created
│   └── repo/
│       ├── ChildRepositoryImpl.kt      ✅ Created
│       └── AppointmentRepositoryImpl.kt ♻️ Updated (path changed)
│
├── ui/
│   ├── component/
│   │   └── AddProfileSheet.kt          ♻️ Updated (Firebase integrated)
│   └── profile/
│       └── viewmodel/
│           ├── ChildViewModel.kt       ✅ Created
│           └── ChildUiState.kt         ✅ Created
│
└── di/
    └── ChildModule.kt                  ✅ Created

Root directory:
├── FIRESTORE_STRUCTURE.md              ✅ Created (Full docs)
├── ADDPROFILE_INTEGRATION_SUMMARY.md   ✅ Created (Summary)
├── QUICK_REFERENCE.md                  ✅ Created (Quick guide)
└── ARCHITECTURE_DIAGRAM.md             ✅ Created (This file)
```

## Testing Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Unit Tests                            │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  CreateChildUseCaseTest                                 │
│    ├─ should return error when userId is empty         │
│    ├─ should return error when name is empty           │
│    ├─ should return error when birthDate is empty      │
│    ├─ should return error when gender is empty         │
│    └─ should call repository when all fields valid     │
│                                                          │
│  GetUserChildrenUseCaseTest                             │
│    ├─ should return error when userId is empty         │
│    └─ should call repository with correct userId       │
│                                                          │
│  ChildRepositoryImplTest                                │
│    ├─ should save child to correct Firestore path      │
│    ├─ should generate ID when not provided             │
│    ├─ should fetch children by userId                  │
│    └─ should order by createdAt                        │
│                                                          │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│                 Integration Tests                        │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  ChildViewModelTest                                     │
│    ├─ should update state to Loading when creating     │
│    ├─ should update state to Success on success        │
│    ├─ should update state to Error on failure          │
│    ├─ should reset state correctly                     │
│    └─ should auto refresh list after create success    │
│                                                          │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│                      UI Tests                            │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  AddProfileSheetTest                                    │
│    ├─ should display all input fields                  │
│    ├─ should disable Add button when fields empty      │
│    ├─ should enable Add button when all filled         │
│    ├─ should show loading indicator during save        │
│    ├─ should dismiss sheet after success               │
│    └─ should show error toast on failure               │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

## Performance Considerations

### Firestore Reads
```
❌ OLD (Flat structure):
   appointments (collection)
     └─ All documents with whereEqualTo("userId", uid)
     Result: Scans ALL documents → Slow for large dataset

✅ NEW (Nested structure):
   users/{userId}/appointments (subcollection)
     └─ Only this user's documents
     Result: Direct access → Fast, no scanning
```

### Indexes
```
Required indexes (auto-created):
- users/{userId}/children.createdAt (Ascending)
- users/{userId}/appointments.createdAt (Descending)

Optional composite indexes:
- users/{userId}/appointments (status, appointmentDate)
- users/{userId}/children (name, birthDate)
```

### Caching Strategy
```
Firestore SDK caches automatically:
- Offline persistence enabled by default
- Queries cache for 30 days
- Manual cache clearing: clearPersistence()

ViewModel StateFlow:
- In-memory cache during app session
- Survives configuration changes
- Cleared on process death
```

## Security Model

```
Firebase Authentication
    │
    └─► Provides: currentUser.uid
        │
        ▼
    ┌───────────────────────────────────┐
    │   Firestore Security Rules       │
    ├───────────────────────────────────┤
    │                                   │
    │  match /users/{userId} {         │
    │    // Only user can access       │
    │    allow read, write:             │
    │      if request.auth.uid == userId│
    │                                   │
    │    match /children/{childId} {   │
    │      // Inherits parent rule     │
    │      allow read, write:           │
    │        if request.auth.uid == userId│
    │    }                              │
    │                                   │
    │    match /appointments/{id} {    │
    │      // Inherits parent rule     │
    │      allow read, write:           │
    │        if request.auth.uid == userId│
    │    }                              │
    │  }                                │
    │                                   │
    └───────────────────────────────────┘
        │
        ▼
    ┌───────────────────────────────────┐
    │         Data Isolation            │
    ├───────────────────────────────────┤
    │                                   │
    │  User A (uid: abc123)             │
    │    └─ users/abc123/               │
    │       ├─ children/                │
    │       └─ appointments/            │
    │                                   │
    │  User B (uid: def456)             │
    │    └─ users/def456/               │
    │       ├─ children/                │
    │       └─ appointments/            │
    │                                   │
    │  ❌ User A cannot access User B   │
    │  ✅ Each user isolated             │
    │                                   │
    └───────────────────────────────────┘
```
