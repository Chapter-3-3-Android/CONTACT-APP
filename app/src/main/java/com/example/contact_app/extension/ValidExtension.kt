package com.example.contact_app.extension

object ValidExtension {
    //이름 (성 이름)
    fun String.validName() =
        Regex("^[A-Za-z가-힣]+(?:[ '-][A-Za-z가-힣]+)*\$").containsMatchIn(this)

    //전화번호 (국가코드 포함)
    fun String.validPhoneNumber() = Regex("^\\s*(?:\\+?(\\d{1,3})[-. (]*)?\\s*((01[016789]{3})[-. )]*)?((\\d{3,4})[-. ]*)+(\\d{4})+\\s*\$").containsMatchIn(this)

    //이메일 정규 표현식
    fun String.validEmail() =
        Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$").matches(this)
}