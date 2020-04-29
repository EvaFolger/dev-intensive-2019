package ru.skillbranch.devintensive.models

class Bender(var status: Status=Status.NORMAL, var question: Question=Question.NAME) {
    fun askQuestion():String=when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION-> Question.PROFESSION.question
        Question.MATERIAL->Question.MATERIAL.question
        Question.BDAY->Question.BDAY.question
        Question.SERIAL->  Question.SERIAL.question
        Question.IDLE->Question.IDLE.question

    }
    fun listenAnswer(answer:String): Pair<String, Triple<Int, Int, Int>>{
        return when{
            question==Question.NAME&&(answer.isEmpty()||answer.first().isLowerCase()||answer.first().isDigit())->
            "Имя должно начинаться с заглавной буквы \n ${question.question} " to status.color
            question==Question.PROFESSION&&(answer.isEmpty()||answer.first().isUpperCase()||answer.first().isDigit())->
                "Профессия должна начинаться со строчной буквы \n ${question.question}" to status.color
            question==Question.MATERIAL&&(answer.isEmpty()||answer.contains(Regex("[0-9]")))->
                "Материал не должен содержать цифр \n ${question.question}" to status.color
            question==Question.BDAY&&(answer.isEmpty()||answer.contains(Regex("\\D")))->
                "Год моего рождения должен содержать только цифры \n ${question.question}" to status.color
            question==Question.SERIAL&&(answer.isEmpty()||answer.length>=8||answer.contains(Regex("\\D")))->
                "Серийный номер содержит только цифры, и их 7 \n ${question.question}" to status.color
             question==Question.IDLE->
                 "Отлично - ты справился \n На этом все, вопросов больше нет" to Status.NORMAL.color
        else ->if(question.answers.contains(answer.toLowerCase())){
            question=question.returnQuestion()
    "Отлично - это правильный ответ \n ${question.question}" to status.color}
    else{
    status=status.nextStatus()
    "Это не правильный ответ   \n ${question.question}" to status.color

    }}}

enum class Status(val color:Triple<Int,Int,Int>){
    NORMAL (Triple(255,255,255)),
    WARNING(Triple(255,120,0)),
    DANGER(Triple(255,60,20)),
    CRITICAL(Triple(255,255,0));
    fun nextStatus():Status{
       return if(this.ordinal<values().lastIndex){
            values()[this.ordinal+1]
        }else{
           values()[0]
        }
    }
}
enum class Question(val question: String, val answers: List<String>){
    NAME("Как меня зовут?", listOf("bender", "бендер")){
        override fun returnQuestion(): Question =PROFESSION },
    PROFESSION("Назови мою профессию?", listOf("сгибальщик","bender")){
        override fun returnQuestion(): Question =MATERIAL },
    MATERIAL("Из чего я сделан?", listOf("metal","iron","wood","металл","дерево")){
        override fun returnQuestion(): Question =BDAY},
    BDAY("Когда меня создали?", listOf("2993")){
        override fun returnQuestion(): Question =SERIAL },
    SERIAL("Мой серийный номер?", listOf("2716057")){
        override fun returnQuestion(): Question =IDLE },
    IDLE("На этом всё, вопросов больше нет", listOf()){
        override fun returnQuestion(): Question =NAME };

    abstract fun returnQuestion():Question

}}