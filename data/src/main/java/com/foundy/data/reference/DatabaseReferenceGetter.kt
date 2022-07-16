package com.foundy.data.reference

import com.google.firebase.database.DatabaseReference

interface DatabaseReferenceGetter {
    operator fun invoke(): DatabaseReference
}