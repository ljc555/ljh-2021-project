Component({
    data: {
        selected: 0,
        color: "#7A7E83",
        selectedColor: "#f43f3b",
        list: [
            {
                pagePath: "/pages/index/index",
                iconPath: "/images/ic_tab_home.png",
                selectedIconPath: "/images/ic_tab_home_cur.png",
                text: "首页"
            },
            {
                pagePath: "/pages/sort/sort",
                iconPath: "/images/ic_tab_daren.png",
                selectedIconPath: "/images/ic_tab_daren_cur.png",
                text: "分类"
            },
            {
                pagePath: "/pages/vip/vip",
                iconPath: "/images/ic_tab_vip.png",
                selectedIconPath: "/images/ic_tab_vip_cur.png",
                text: "会员"
            },
            {
                pagePath: "/pages/strategy/strategy",
                iconPath: "/images/ic_tab_strategy.png",
                selectedIconPath: "/images/ic_tab_strategy_cur.png",
                text: "攻略"
            },
            {
                pagePath: "/pages/mine/mine",
                iconPath: "/images/ic_tab_mine.png",
                selectedIconPath: "/images/ic_tab_mine_cur.png",
                text: "我的"
            }
        ],

    },
    methods: {
        switchTab(e) {
            const data = e.currentTarget.dataset
            const url = data.path
            wx.switchTab({url})
            this.setData({
                selected: data.index
            })
        }
    }
})