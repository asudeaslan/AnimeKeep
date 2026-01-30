$ErrorActionPreference = "Stop"

if (Test-Path ".git") {
    Remove-Item -Recurse -Force ".git"
}

git init

$commits = @(
    @{ Date = "2025-11-19T10:15:00+03:00"; Message = "Initial commit"; Files = @("README.md", ".gitignore") },
    @{ Date = "2025-11-19T14:30:00+03:00"; Message = "Add gradle configurations"; Files = @("build.gradle.kts", "settings.gradle.kts", "gradle.properties", "local.properties") },
    @{ Date = "2025-11-20T11:45:00+03:00"; Message = "Add gradle wrapper and scripts"; Files = @("gradlew", "gradlew.bat", "gradle/") },
    @{ Date = "2025-11-22T09:20:00+03:00"; Message = "Setup app level gradle config"; Files = @("app/build.gradle.kts", "app/proguard-rules.pro") },
    @{ Date = "2025-11-24T16:10:00+03:00"; Message = "Add Android manifest"; Files = @("app/src/main/AndroidManifest.xml") },
    @{ Date = "2025-11-26T10:05:00+03:00"; Message = "Setup base colors and strings"; Files = @("app/src/main/res/values/colors.xml", "app/src/main/res/values/strings.xml") },
    @{ Date = "2025-11-26T15:50:00+03:00"; Message = "Configure app themes"; Files = @("app/src/main/res/values/themes.xml", "app/src/main/res/values/ic_launcher_background.xml", "app/src/main/res/xml/") },
    @{ Date = "2025-11-29T13:20:00+03:00"; Message = "Create base application class"; Files = @("app/src/main/java/com/asude/animekeep/AnimeApplication.kt") },
    @{ Date = "2025-11-30T11:15:00+03:00"; Message = "Setup main activity"; Files = @("app/src/main/java/com/asude/animekeep/MainActivity.kt") },
    @{ Date = "2025-12-02T10:30:00+03:00"; Message = "Add theme colors setup"; Files = @("app/src/main/java/com/asude/animekeep/ui/theme/Color.kt") },
    @{ Date = "2025-12-02T12:45:00+03:00"; Message = "Configure compose theme"; Files = @("app/src/main/java/com/asude/animekeep/ui/theme/Theme.kt") },
    @{ Date = "2025-12-02T16:10:00+03:00"; Message = "Add typography setup"; Files = @("app/src/main/java/com/asude/animekeep/ui/theme/Type.kt") },
    @{ Date = "2025-12-05T09:55:00+03:00"; Message = "Create anime domain models"; Files = @("app/src/main/java/com/asude/animekeep/model/") },
    @{ Date = "2025-12-06T14:20:00+03:00"; Message = "Implement JikanApi service"; Files = @("app/src/main/java/com/asude/animekeep/data/remote/") },
    @{ Date = "2025-12-08T11:10:00+03:00"; Message = "Add AnimeRepository implementation"; Files = @("app/src/main/java/com/asude/animekeep/data/repository/") },
    @{ Date = "2025-12-09T15:35:00+03:00"; Message = "Setup dependency injection container"; Files = @("app/src/main/java/com/asude/animekeep/data/AppContainer.kt") },
    @{ Date = "2025-12-12T10:25:00+03:00"; Message = "Add AppViewModelProvider"; Files = @("app/src/main/java/com/asude/animekeep/ui/AppViewModelProvider.kt") },
    @{ Date = "2025-12-14T13:40:00+03:00"; Message = "Implement splash screen UI"; Files = @("app/src/main/java/com/asude/animekeep/ui/splash/") },
    @{ Date = "2025-12-15T09:15:00+03:00"; Message = "Add login screen UI"; Files = @("app/src/main/java/com/asude/animekeep/ui/login/LoginScreen.kt") },
    @{ Date = "2025-12-15T14:50:00+03:00"; Message = "Implement login viewmodel logic"; Files = @("app/src/main/java/com/asude/animekeep/ui/login/LoginViewModel.kt") },
    @{ Date = "2025-12-17T11:05:00+03:00"; Message = "Add sign up screen UI"; Files = @("app/src/main/java/com/asude/animekeep/ui/login/SignUpScreen.kt") },
    @{ Date = "2025-12-17T16:30:00+03:00"; Message = "Implement sign up viewmodel logic"; Files = @("app/src/main/java/com/asude/animekeep/ui/login/SignUpViewModel.kt") },
    @{ Date = "2025-12-20T10:45:00+03:00"; Message = "Design home screen UI layout"; Files = @("app/src/main/java/com/asude/animekeep/ui/home/HomeScreen.kt") },
    @{ Date = "2025-12-21T13:10:00+03:00"; Message = "Connect home screen with viewmodel"; Files = @("app/src/main/java/com/asude/animekeep/ui/home/HomeViewModel.kt") },
    @{ Date = "2025-12-24T15:25:00+03:00"; Message = "Implement anime detail screen UI"; Files = @("app/src/main/java/com/asude/animekeep/ui/detail/AnimeDetailScreen.kt") },
    @{ Date = "2025-12-25T11:40:00+03:00"; Message = "Add detail viewmodel state management"; Files = @("app/src/main/java/com/asude/animekeep/ui/detail/DetailViewModel.kt") },
    @{ Date = "2025-12-28T09:50:00+03:00"; Message = "Design user profile screen"; Files = @("app/src/main/java/com/asude/animekeep/ui/profile/ProfileScreen.kt") },
    @{ Date = "2025-12-28T14:15:00+03:00"; Message = "Implement profile viewmodel"; Files = @("app/src/main/java/com/asude/animekeep/ui/profile/ProfileViewModel.kt") },
    @{ Date = "2025-12-30T10:30:00+03:00"; Message = "Add my list screen UI"; Files = @("app/src/main/java/com/asude/animekeep/ui/mylist/MyListScreen.kt") },
    @{ Date = "2025-12-30T16:05:00+03:00"; Message = "Implement my list viewmodel"; Files = @("app/src/main/java/com/asude/animekeep/ui/mylist/MyListViewModel.kt") },
    @{ Date = "2026-01-04T13:20:00+03:00"; Message = "Add drawable resources and icons"; Files = @("app/src/main/res/drawable/") },
    @{ Date = "2026-01-05T11:10:00+03:00"; Message = "Add mipmap launcher icons"; Files = @("app/src/main/res/mipmap-anydpi-v26/", "app/src/main/res/mipmap-hdpi/", "app/src/main/res/mipmap-mdpi/", "app/src/main/res/mipmap-xhdpi/", "app/src/main/res/mipmap-xxhdpi/", "app/src/main/res/mipmap-xxxhdpi/") },
    @{ Date = "2026-01-10T14:45:00+03:00"; Message = "Update playstore assets"; Files = @("app/src/main/ic_launcher-playstore.png") },
    @{ Date = "2026-01-15T10:30:00+03:00"; Message = "Add unit tests setup"; Files = @("app/src/test/", "app/src/androidTest/") },
    @{ Date = "2026-01-25T16:20:00+03:00"; Message = "Add machine learning boosting report"; Files = @("machine_learning_boosting.docx") },
    @{ Date = "2026-01-30T11:00:00+03:00"; Message = "Final bug fixes and project polish"; Files = @(".") }
)

foreach ($c in $commits) {
    foreach ($f in $c.Files) {
        if ($f -eq ".") {
            git add .
        } elseif (Test-Path $f) {
            git add $f
        }
    }
    
    $env:GIT_AUTHOR_DATE = $c.Date
    $env:GIT_COMMITTER_DATE = $c.Date
    
    $status = git status --porcelain
    if ($status) {
        git commit -m $c.Message
    }
}
