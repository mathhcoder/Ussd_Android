package tech.appme.ussd.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.News

class NewsViewModel : BaseViewModel(){

    private val newsData = MutableLiveData<ArrayList<News>>()
    var news: LiveData<ArrayList<News>> = newsData


    init{
        newsData.postValue(getNews())

    }


    private fun getNews() : ArrayList<News>{
        var news = ArrayList<News>()
        var new1 = News(
            1,
            1609836385000,
            "Mobiuz 1,5 milliard so'mdan ko‘proq sovrin fondiga ega o‘yin o‘tkazmoqda",
            "Mobiuz празднует 6-летие деятельности на рынке мобильной связи",
            "Mobiuz o‘zining tug‘ilgan kuni munosabati bilan abonentlar uchun ko‘plab hushxabarlar tayyorladi.\n" +
                    "\n" +
                    "2014-yilning 1-dekabrida Mobiuz uyali aloqa operatori (“UMS” MChJ) ofislari eshiklari ilk mijozlar uchun ochilgandi. Bozorga kirib kelgan kompaniya noyob V&D tariflarini (bir vaqtning o‘zida daqiqalar, MB va SMS to‘plamlarini o‘z ichiga olgan) hamda qulay xizmatlarni taqdim etdi. Shu kuni operatorga 19 281 ta abonent ulandi.\n" +
                    "\n" +
                    "Mobiuz jamoasi 6 yil davomida mijozlarga dolzarb va talablarga mos mahsulot va xizmatlarni taqdim etishga intilib kelmoqda. Bunday mahsulotlar qatorida – “Mobi” tariflar turkumi, “Eksklyuziv” tarifi, \"Mutlaqo cheksiz\" aksiyasi, Internet uchun arzonlashtirilgan narxlar, \"Super 0\" xizmati va boshqalardir. Shuningdek, har yili kompaniya abonentlariga moliyaviy axvollarini sezilarli darajada yaxshilash imkoniyatini yaratib, turli xil qimmatbaho sovg‘alarni yutib olish imkoniyatini berib kelmoqda.\n" +
                    "\n" +
                    "Mobil aloqa operatori texnik rivojlanishga ham alohida e‘tibor berib kelmoqda. Hususan, 2016-yilda Toshkent shahri hududi 4G LTE tarmog‘I bilan qoplandi, 2017-yilda ushbu tarmoq Samarqand shahrida, 2019-yildan esa barcha viloyat va tuman markazlarida, yirik shaharlarda va bir qator qishloqlarda ishga tushirildi. Joriy yilning 11 oyi ichida respublika bo‘ylab 1300 dan ortiq tayanch stantsiyalari ishga tushirildi.",
            "В честь своего дня рождения Mobiuz подготовил множество сюрпризов для абонентов"+
                    "1 декабря 2014 года мобильный оператор Mobiuz (ООО «UMS») открыл двери своих офисов для первых клиентов. Выходя на рынок, компания представила уникальные V&D тарифы (включают одновременно пакеты минут, МБ и SMS) и целый спектр удобных услуг. В тот же день к оператору подключилось впечатляющее количество абонентов – 19 281.",
            "https://company.mobi.uz/uz/press/2020/21887/",
            "https://company.mobi.uz/upload/images/content-img(121).png"
        )
        news.add(new1)
        var new2 = News(
            1,
            1609836385000,
            "Mobiuz 1,5 milliard so'mdan ko‘proq sovrin fondiga ega o‘yin o‘tkazmoqda",
            "Mobiuz празднует 6-летие деятельности на рынке мобильной связи",
            "Mobiuz o‘zining tug‘ilgan kuni munosabati bilan abonentlar uchun ko‘plab hushxabarlar tayyorladi.\n" +
                    "\n" +
                    "2014-yilning 1-dekabrida Mobiuz uyali aloqa operatori (“UMS” MChJ) ofislari eshiklari ilk mijozlar uchun ochilgandi. Bozorga kirib kelgan kompaniya noyob V&D tariflarini (bir vaqtning o‘zida daqiqalar, MB va SMS to‘plamlarini o‘z ichiga olgan) hamda qulay xizmatlarni taqdim etdi. Shu kuni operatorga 19 281 ta abonent ulandi.\n" +
                    "\n" +
                    "Mobiuz jamoasi 6 yil davomida mijozlarga dolzarb va talablarga mos mahsulot va xizmatlarni taqdim etishga intilib kelmoqda. Bunday mahsulotlar qatorida – “Mobi” tariflar turkumi, “Eksklyuziv” tarifi, \"Mutlaqo cheksiz\" aksiyasi, Internet uchun arzonlashtirilgan narxlar, \"Super 0\" xizmati va boshqalardir. Shuningdek, har yili kompaniya abonentlariga moliyaviy axvollarini sezilarli darajada yaxshilash imkoniyatini yaratib, turli xil qimmatbaho sovg‘alarni yutib olish imkoniyatini berib kelmoqda.\n" +
                    "\n" +
                    "Mobil aloqa operatori texnik rivojlanishga ham alohida e‘tibor berib kelmoqda. Hususan, 2016-yilda Toshkent shahri hududi 4G LTE tarmog‘I bilan qoplandi, 2017-yilda ushbu tarmoq Samarqand shahrida, 2019-yildan esa barcha viloyat va tuman markazlarida, yirik shaharlarda va bir qator qishloqlarda ishga tushirildi. Joriy yilning 11 oyi ichida respublika bo‘ylab 1300 dan ortiq tayanch stantsiyalari ishga tushirildi.",
            "В честь своего дня рождения Mobiuz подготовил множество сюрпризов для абонентов"+
                    "1 декабря 2014 года мобильный оператор Mobiuz (ООО «UMS») открыл двери своих офисов для первых клиентов. Выходя на рынок, компания представила уникальные V&D тарифы (включают одновременно пакеты минут, МБ и SMS) и целый спектр удобных услуг. В тот же день к оператору подключилось впечатляющее количество абонентов – 19 281.",
            "https://company.mobi.uz/uz/press/2020/21887/",
        )
        news.add(new2)

        return news



    }
}