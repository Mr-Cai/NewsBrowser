package news.browser.module.news.model

import news.browser.base.BaseCommand

class NewsCommand(var page: Int, var num: Int, val key: String = "53a9f2b7b849346cb13fb4b56cac9ec8") : BaseCommand()