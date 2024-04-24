package com.example.contact_app.extension

object ValidExtension {
    //이름 (성 이름)
    fun String.validName() =
        Regex("/^[A-Za-z가-힣]+(?:[ '-][A-Za-z가-힣]+)*\$").containsMatchIn(this)

    //전화번호 (국가코드 포함)
    fun String.validPhoneNumber() = Regex("^\\s*(?:\\+?(\\d{1,3})[-. (]*)?\\s*((01[016789]{3})[-. )]*)?((\\d{3,4})[-. ]*)+(\\d{4})+\\s*\$").containsMatchIn(this)

    //이메일 정규 표현식
    fun String.validEmail() =
        Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$").matches(this)

    // 영어나 한글이나 숫자로 1~20자 사이
    fun String.validDate() =
        Regex("^[A-Za-zㄱ-ㅣ가-힣0-9]{1,20}\$").matches(this)

    // 리마인드 시간 (분 단위, 3자리까지)
    fun String.validRemindTime() =
        Regex("^[1-9]\\d{0,2}\$").matches(this)
}