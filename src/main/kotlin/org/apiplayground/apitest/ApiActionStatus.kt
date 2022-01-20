package org.apiplayground.apitest

import org.springframework.http.HttpStatus

public sealed class ApiActionStatus(public val value: HttpStatus) {

    /* Used when the action request was received and execution is pending */
    public data class OperationPending(val status: HttpStatus = HttpStatus.OK) : ApiActionStatus(status)

    /* Used when the action succeeds */
    public data class OperationSuccessStatus(val status: HttpStatus = HttpStatus.OK) : ApiActionStatus(status)

    /* Used when the action didn't produce an error but didn't really have any other effect either */
    public data class OperationFailedStatus(val status: HttpStatus = HttpStatus.OK) : ApiActionStatus(status)

    /* Used when the action needs authorization or higher privilege level */
    public data class OperationAuthorizationFailureStatus(val status: HttpStatus = HttpStatus.UNAUTHORIZED) :
        ApiActionStatus(status)

    /* Used when the action needs authorization or higher privilege level */
    public data class OperationPrivilegeFailureStatus(val status: HttpStatus = HttpStatus.FORBIDDEN) :
        ApiActionStatus(status)

    /* Used when the action required params are incorrect or missing */
    public data class OperationParamsFailureStatus(val status: HttpStatus = HttpStatus.BAD_REQUEST) :
        ApiActionStatus(status)

    /* Used when the action required params are incorrect or missing */
    public data class OperationInvalidParamsFailureStatus(val status: HttpStatus = HttpStatus.UNPROCESSABLE_ENTITY) :
        ApiActionStatus(status)

    /* User when the action was dependent on a specific time frame that has now expired */
    public data class OperationFailedExpiredTimeframeStatus(val status: HttpStatus = HttpStatus.MOVED_PERMANENTLY) :
        ApiActionStatus(status)

    /* Used when the action results in an exception or irrecoverable error */
    public data class OperationErrorFailureStatus(val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) :
        ApiActionStatus(status)

    /* Used when the action results in an exception because of a conflict */
    public data class OperationConflictStatus(val status: HttpStatus = HttpStatus.CONFLICT) : ApiActionStatus(status)

    /* Used when the action results in an exception because user is blocked */
    public data class OperationBlocked(val status: HttpStatus = HttpStatus.BAD_GATEWAY) : ApiActionStatus(status)
}