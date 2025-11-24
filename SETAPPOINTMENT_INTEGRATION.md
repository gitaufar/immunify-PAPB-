# SetAppointmentScreen Firebase Integration

## Perubahan

### Sebelum
- `user.children` diambil dari UserData yang di-pass dari navigation
- Children list selalu kosong karena hardcoded `emptyList()`
- Tidak ada koneksi dengan Firestore

### Sesudah
- Children di-fetch langsung dari Firestore menggunakan `ChildViewModel`
- Data diambil dari path: `users/{userId}/children`
- Real-time loading state dan error handling
- Convert domain model `Child` ke `ChildData` untuk UI compatibility

## Flow

```
SetAppointmentScreen Launch
    ↓
LaunchedEffect(user.id)
    ↓
childViewModel.getUserChildren(user.id)
    ↓
Fetch from: users/{userId}/children
    ↓
State: ChildUiState.Loading → Show CircularProgressIndicator
    ↓
State: ChildUiState.ChildrenLoaded → Convert Child to ChildData
    ↓
Display in VaccinantSection
```

## Mapping

```kotlin
Child (Domain Model)              ChildData (UI Model)
├─ id: String              →     ├─ id: String
├─ userId: String                 │
├─ name: String            →     ├─ name: String
├─ birthDate: String       →     ├─ birthDate: String
├─ gender: String          →     └─ gender: Gender (MALE/FEMALE)
├─ createdAt: Long
└─ updatedAt: Long
```

## UI States

1. **Loading**: Show CircularProgressIndicator
2. **Error**: Show error message
3. **ChildrenLoaded**: Display children in VaccinantSection
4. **Idle**: Show VaccinantSection (empty or with data)

## Code Changes

### SetAppointmentScreen.kt

**Added:**
- `ChildViewModel` injection via `hiltViewModel()`
- `userChildrenState` observation with `collectAsState()`
- `LaunchedEffect` to fetch children on screen launch
- Domain to UI model conversion (Child → ChildData)
- Loading/error state handling

**Modified:**
- `VaccinantSection` now receives `childrenList` from Firestore instead of `user.children`

### RootNavGraph.kt

**Updated:**
- Comment changed: "Loaded dynamically in SetAppointmentScreen"
- `children = emptyList()` kept but documented as placeholder

## Testing

1. **Login** dengan Firebase Auth
2. **Add child** via AddProfileSheet (atau manual di Firestore)
3. **Navigate** ke SetAppointmentScreen (pilih clinic dari map)
4. **Verify**:
   - Loading indicator muncul saat fetch
   - Children list ditampilkan setelah loaded
   - Checkbox untuk select vaccinants berfungsi

## Benefits

✅ Real-time data dari Firestore
✅ Loading state untuk UX yang baik
✅ Error handling jika fetch gagal
✅ No hardcoded data
✅ Sinkron dengan AddProfileSheet
✅ Data isolation per user (security)
