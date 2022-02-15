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
            "Zeitgeist 2010_ Year in Review",
            "Google Demo Slam_ 20ft Search",
            "Introducing Gmail Blue",
            "Introducing Google Fiber to the Pole",
            "Introducing Google Nose"
        )

        val description = "Fusce id nisi turpis. Praesent viverra bibendum semper. " +
                "Donec tristique, orci sed semper lacinia, quam erat rhoncus massa, non congue tellus est " +
                "quis tellus. Sed mollis orci venenatis quam scelerisque accumsan. Curabitur a massa sit " +
                "amet mi accumsan mollis sed et magna. Vivamus sed aliquam risus. Nulla eget dolor in elit " +
                "facilisis mattis. Ut aliquet luctus lacus. Phasellus nec commodo erat. Praesent tempus id " +
                "lectus ac scelerisque. Maecenas pretium cursus lectus id volutpat."
        val studio = arrayOf(
            "Studio Zero",
            "Studio One",
            "Studio Two",
            "Studio Three",
            "Studio Four"
        )
        val videoUrl = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
            "http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4",
            "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4",
            "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4",
            "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4"
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