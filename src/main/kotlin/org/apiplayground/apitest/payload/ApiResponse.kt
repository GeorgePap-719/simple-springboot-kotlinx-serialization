
package org.apiplayground.apitest.payload

import org.apiplayground.apitest.errors.ApiError
import org.springframework.http.HttpStatus


/**
 * Simple custom [ApiResponse] factory function with hashmap data, error, mHeaders and mHttpStatus.
 * Has almost no overhead over call to [SharedPayload] constructor.
 */
public fun ApiResponse(
    data: Any?,
    error: ApiError? = null,
    mHttpStatus: HttpStatus = HttpStatus.OK,
    mHeaders: HashMap<String, String> = HashMap()
): ApiResponse = buildSharedPayload {
    data?.let { data(data) }
    error?.let { error(error) }
    headers(mHeaders)
    httpStatus(mHttpStatus)
}

public typealias ApiResponse = SharedPayload









