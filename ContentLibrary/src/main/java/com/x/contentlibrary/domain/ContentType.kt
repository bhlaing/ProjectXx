package com.x.contentlibrary.domain

enum class ContentType {
    TEXT {
        override fun toString(): String {
            return "Text"
        }
    },
    MOBILE {
        override fun toString(): String {
            return "Mobile"
        }
    },
    NUMBER {
        override fun toString(): String {
            return "Number"
        }
    },
    WIFI_INFO {
        override fun toString(): String {
            return "Wifi information"
        }
    },
    PAY_ID {
        override fun toString(): String {
            return "Pay ID"
        }
    },
    CONTACT_DETAILS {
        override fun toString(): String {
            return "Contact details"
        }
    }
}