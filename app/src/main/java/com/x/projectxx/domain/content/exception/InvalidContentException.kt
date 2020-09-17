package com.x.projectxx.domain.content.exception

import com.x.projectxx.domain.exceptions.GenericException

class InvalidContentException(errorMessage: Int?) : GenericException(errorMessage)