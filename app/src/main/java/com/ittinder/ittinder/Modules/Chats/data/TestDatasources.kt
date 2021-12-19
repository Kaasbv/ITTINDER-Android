package com.ittinder.ittinder.Modules.Chats.data

import com.ittinder.ittinder.Modules.Chats.model.CatUiModel

class TestDatasources {
    fun loadCats(): List<CatUiModel> {
        return (
                listOf(
                    CatUiModel(
                        "Stephan",
                        "I send you my dick, pls respond",
                        "https://d277f5wvbpn3b3.cloudfront.net/images/teammembers/stephan.jpg"
                    ),
                    CatUiModel(
                        "Nassim",
                        "Show bob en vagene",
                        "https://d277f5wvbpn3b3.cloudfront.net/images/teammembers/nassim.jpg"
                    ),
                )
                )
    }

}
