// components/product-item/product-item.js
const app = getApp()

Component({
  options: {
    multipleSlots: true // 在组件定义时的选项中启用多slot支持
  },
  /**
   * 组件的属性列表
   */
  properties: {
    productItem: {
      type: Object,
      value: {},
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    windowWidth: app.globalData.windowWidth,

  },

  lifetimes: {
    attached: function () {
      // let productItem = this.data.productItem
      // (productItem.priceInfo.lowestCouponPrice ?
      //     productItem.priceInfo.lowestCouponPrice:productItem.priceInfo.price) - productItem.commissionInfo.commission
    },
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
