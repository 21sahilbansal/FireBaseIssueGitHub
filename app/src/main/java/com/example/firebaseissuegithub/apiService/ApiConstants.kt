package com.example.firebaseissuegithub.apiService

class ApiConstants {
    companion object {
        const val GITHUB_BASE_URL = "https://api.github.com/repos/firebase/firebase-ios-sdk/"
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"

        object ApiServiceConstants {
            const val Issues = "issues"
            const val ISSUES_NUMBER_COMMENT = "issues/{number}/comments"
        }
    }

}