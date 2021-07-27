package com.saturn

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class CleanDbExtension : Extension, BeforeTestExecutionCallback {
    override fun beforeTestExecution(context: ExtensionContext?) {
        val cleaner = SpringExtension.getApplicationContext(context!!).getBean(DbCleaner::class.java)
        cleaner.clean()
    }
}