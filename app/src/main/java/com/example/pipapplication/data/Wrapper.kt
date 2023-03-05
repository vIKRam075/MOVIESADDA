package com.example.pipapplication.data

enum class Status {
    SUCCESS,
    API_ERROR,
    HTTP_ERROR,
    LOADING
}

data class Wrapper<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Wrapper<T> =
            Wrapper(
                Status.SUCCESS,
                data,
                null
            )

        fun <T> apiError(data: T?, message: String?): Wrapper<T> =
            Wrapper(
                Status.API_ERROR,
                data,
                message
            )

        fun <T> httpError(message: String?): Wrapper<T> =
            Wrapper(
                Status.HTTP_ERROR,
                null,
                message
            )

        fun <T> loading(): Wrapper<T> =
            Wrapper(
                Status.LOADING,
                null,
                null
            )
    }
}
