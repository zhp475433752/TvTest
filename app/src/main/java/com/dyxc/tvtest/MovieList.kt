package com.dyxc.tvtest

object MovieList {
    val MOVIE_CATEGORY = arrayOf(
        "Category Zero",
        "Category One",
        "Category Two",
        "Category Three",
        "Category Four",
        "Category Five"
    )

    val list: List<Movie> by lazy {
        setupMovies()
    }
    private var count: Long = 0

    private fun setupMovies(): List<Movie> {
        val title = arrayOf(
            "窦老师带你了解豆伴匠",
            "Google Demo Slam_ 20ft Search",
            "Introducing Gmail Blue",
            "Introducing Google Fiber to the Pole",
            "Introducing Google Nose"
        )

        val description = "窦老师带你了解豆伴匠---大唐奇闻录-01"
        val studio = arrayOf(
            "Studio Zero",
            "Studio One",
            "Studio Two",
            "Studio Three",
            "Studio Four"
        )
        val videoUrl = arrayOf(
            "https://vod.douyuxingchen.com/6057e0cbvodtransbj1306665185/847f69ee3701925924906167486/v.f100830.mp4",
            "https://vod.douyuxingchen.com/6057e0cbvodtransbj1306665185/847f69ee3701925924906167486/v.f100830.mp4",
            "https://vod.douyuxingchen.com/6057e0cbvodtransbj1306665185/847f69ee3701925924906167486/v.f100830.mp4",
            "https://vod.douyuxingchen.com/6057e0cbvodtransbj1306665185/847f69ee3701925924906167486/v.f100830.mp4",
            "https://vod.douyuxingchen.com/6057e0cbvodtransbj1306665185/847f69ee3701925924906167486/v.f100830.mp4"
        )
        val bgImageUrl = arrayOf(
            "https://c-ssl.duitang.com/uploads/item/201802/06/20180206165118_WL4Hm.thumb.1000_0.jpeg",
            "https://img9.doubanio.com/view/thing_review/l/public/p1062915.jpg",
            "https://img.zcool.cn/community/0171745644506832f87512f6b54de8.jpg@1280w_1l_2o_100sh.jpg",
            "https://img9.51tietu.net/pic/2019-091003/cqqmo322ijscqqmo322ijs.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fd.zdqx.com%2Fdianydisa_20181107%2F001.jpg"
        )
        val cardImageUrl = arrayOf(
            "https://c-ssl.duitang.com/uploads/item/201802/06/20180206165118_WL4Hm.thumb.1000_0.jpeg",
            "https://img9.doubanio.com/view/thing_review/l/public/p1062915.jpg",
            "https://img.zcool.cn/community/0171745644506832f87512f6b54de8.jpg@1280w_1l_2o_100sh.jpg",
            "https://img9.51tietu.net/pic/2019-091003/cqqmo322ijscqqmo322ijs.jpg",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fd.zdqx.com%2Fdianydisa_20181107%2F001.jpg"
        )

        val list = title.indices.map {
            buildMovieInfo(
                title[it],
                description,
                studio[it],
                videoUrl[it],
                cardImageUrl[it],
                bgImageUrl[it]
            )
        }

        return list
    }

    private fun buildMovieInfo(
        title: String,
        description: String,
        studio: String,
        videoUrl: String,
        cardImageUrl: String,
        backgroundImageUrl: String
    ): Movie {
        val movie = Movie()
        movie.id = count++
        movie.title = title
        movie.description = description
        movie.studio = studio
        movie.cardImageUrl = cardImageUrl
        movie.backgroundImageUrl = backgroundImageUrl
        movie.videoUrl = videoUrl
        return movie
    }
}