<template>
	<view class="seller-detail">

		<!-- 导航栏 -->
		<u-navbar bgColor="transparent" fixed safeAreaInsetTop autoBack>
			<u-icon name="arrow-left" color="#fff" size="20px" slot="left"></u-icon>
			<view slot="right" class="u-flex">
				<u-icon size="24px" name="search" color="#fff"></u-icon>
				<u-icon size="24px" :name="isFav ? 'star-fill' : 'star'" :color="isFav ? '#f29100' : '#fff'"
					style="margin-left: 10px;" @click="collectSeller"></u-icon>
				<u-icon size="24px" name="more-dot-fill" color="#fff" style="margin-left: 10px;"></u-icon>
			</view>
		</u-navbar>

		<!-- 店招背景 -->
		<view class="seller-cover" :style="'background: '+ detail.cover"></view>


		<view class="seller-body">

			<!-- 商家信息 -->
			<u-gap height="10px"></u-gap>
			<view class="u-flex wrap" style="align-items: flex-start;">
				<view class="seller-info-wrap">
					<h2 class="seller-name">{{detail.name}}</h2>
					<view class="seller-detail-info-wrap">
						<u-icon name="star-fill" color="#f76343" :size="12" :labelSize="12" :label="detail.score"
							labelColor="#f76343"></u-icon>
						<u-text :text="'月订单' + detail.monthOrderNum" color="#999" :size="12"></u-text>
						<u-text :text="'配送约' + detail.avgDeliveryTime + '分钟'" color="#999" :size="12"></u-text>
					</view>
					<u-gap height="5"></u-gap>
					<view class="u-flex u-flex-row-start activities-wrap">
						<Tag v-for="item in detail.coupons" type="error" desc="领">
							<u-icon slot="text" name="rmb" color="#fff" labelColor="#fff" size="12px" labelSize="13px"
								space="0" :label="item.price"></u-icon>
						</Tag>
					</view>
					<view class="u-flex u-flex-row-start coupons-wrap">
						<u-tag v-for="item in detail.activities" :text="discountStr(item, 1)" type="error" size="mini"
							plain plainFill>
						</u-tag>
					</view>
					<u-gap height="5"></u-gap>
					<u-notice-bar style="padding: 0;" v-if="detail.notice" :text="detail.notice" fontSize="12"
						bgColor="transparent" :speed="0" direction="8000"></u-notice-bar>
				</view>
				<image class="seller-avatar" :src="detail.avatar" mode="aspectFill"></image>
			</view>
			<u-gap height="10px"></u-gap>


			<!-- 店铺分栏tabs -->
			<u-tabs :list="menuList" lineWidth="30" lineColor="#f29100"></u-tabs>
			<u-divider></u-divider>

			<!-- 点单 -->
			<view v-show="menuActive === 0" class="goods-container">
				<scroll-view :scroll-y="true" style="width: auto;">
					<view class="category-wrap">
						<view v-for="(item,index) in categoryList" :key="index" @click="changeCategory(index)"
							:class="{'active' : index === categoryActive}">
							<view class="category-name u-line-2">
								{{item.name}}
							</view>
							<!-- 商品加购数量 -->
							<u-badge type="error" max="99" :value="item.basketNum" absolute :offset="['4px', '6px']">
							</u-badge>
						</view>
					</view>
				</scroll-view>

				<view class="goods-wrap">
					<scroll-view :scroll-y="true">
						<view style="height: 440px">
							<!-- + '-' + item.quantity -->
							<FoodItem v-for="(item,index) in goodsList" :item="item" :key="item.id"
								@clickBasketNum="onClickBasketNum" @clickSku="onClickSku" @change="onUpdateQuantity" />
						</view>
					</scroll-view>
				</view>
			</view>

			<!-- 评价 -->
			<view v-show="menuActive === 1" class="evaluate-wrap">
				<view v-for="(item,index) in evaluateList" :key="item.id">
					<CommentItem :item="item">
						<u-icon :label="item.likeNum" labelSize="15" size="23" color="#999" labelPos="left"
							name="thumb-up">
						</u-icon>
						<template slot="desc">
							<u-rate :value="item.score" :count="5" :gutter="2" :size="14" activeColor="#f7df62"
								disabled></u-rate>
							<u-gap height="1"></u-gap>
						</template>
					</CommentItem>
					<view class="seller-reply-wrap">
						<h3>商家回复({{item.replyTime || '自动回复'}})</h3>
						<u-text :text="item.replyContent || '欢迎下次光临！'" color="#666" size="12"></u-text>
					</view>
					<u-gap height="5"></u-gap>
				</view>
				<u-gap height="60"></u-gap>
			</view>

			<!-- 商家 -->
			<view v-show="menuActive === 2"></view>

		</view>

		<!-- 结算购物车统计栏 -->
		<view class="statistics-bar" @click="basketList.length && (basketActionSheet.show = true)">
			<view class="u-flex">
				<view class="basket-icon-wrap">
					<u-icon name="shopping-cart-fill" size="34"
						:color="statisticsBar.totalCount > 0 ? '#fff' : 'rgba(255, 255, 255, .5)'"></u-icon>
					<u-badge type="error" max="99" :value="statisticsBar.totalCount"></u-badge>
				</view>
				<view style="margin-left: 9px;">
					<u-icon name="rmb" :label="basketActionSheet.discount.finalPrice || statisticsBar.totalPrice"
						color="#fff" labelColor="#fff" :size="16" :labelSize="20" bold></u-icon>
					<view class="u-flex u-flex-row-start" v-if="detail.deliveryFee > 0">
						<u-text text="预估另需配送费" color="#fff" size="12px"></u-text>
						<u-icon name="rmb" :label="detail.deliveryFee" color="#fff" labelColor="#fff" size="12px"
							labelSize="12px" space="0"></u-icon>
					</view>
				</view>
			</view>
			<view class="statistics-bar-right-info-wrap">
				<view class="purchase-tips-wrap" v-show="!statisticsBar.canBuy">
					<u-icon v-show="statisticsBar.totalPrice < detail.minDeliveryPrice" name="rmb"
						:label="detail.minDeliveryPrice + '起送'" color="rgba(255, 255, 255, .5)"
						labelColor="rgba(255, 255, 255, .5)" size="13" labelSize="13" style="flex: 1;" space="0">
					</u-icon>
					<u-text v-show="!statisticsBar.hasMandatory" color="rgba(255, 255, 255, .5)" text="未点必点品" size="12">
					</u-text>
				</view>
				<u-button v-show="statisticsBar.canBuy" class="purchase-btn" type="warning" @click="orderGoods">购买
				</u-button>
			</view>
		</view>


		<!-- 购物车action-sheet -->
		<u-action-sheet :show="basketActionSheet.show" closeOnClickAction round @close="basketActionSheet.show = false"
			round="8px">
			<view class="discount-tip-wrap u-flex" v-show="basketActionSheet.discount.reduce > 0">
				<u-text text="已享" color="#333" size="12px" bold></u-text>
				<u-text color="#FFD000" :text="discountStr(basketActionSheet.discount, 2)" size="12px" bold></u-text>
			</view>
			<u-gap height="15px"></u-gap>
			<view class="u-flex wrap">
				<view class="u-flex">
					<u-text text="打包费" size="12px" color="#333" bold></u-text>
					<u-icon name="rmb" size="12px" labelSize="13px" space="0" color="#d66156" labelColor="#d66156" bold
						:label="basketActionSheet.packageFee"></u-icon>
				</view>
				<u-icon name="trash" label="清空购物车" color="#666" labelColor="#666" size="12px" labelSize="12px" bold
					@tap="clearBasket"></u-icon>
			</view>
			<u-divider lineColor="#f1eef3"></u-divider>
			<scroll-view :scroll-y="true">
				<view class="basket-goods-wrap">
					<FoodItem v-for="(item,index) in basketList" :item="formatBasketGoods(item)"
						:key="item.id + '_' + item.goods.id" size="mini" @change="onUpdateQuantity">
					</FoodItem>
					<u-gap height="60"></u-gap>
				</view>
			</scroll-view>
		</u-action-sheet>

		<!-- 商品加购数量modal -->
		<u-modal :show="basketNumModal.show" @confirm="onConfirmBasketNum" title="设置购买数量"
			@close="basketNumModal.show = false" closeOnClickOverlay>
			<u-input placeholder="请输入加购数量" v-model="basketNumModal.quantity" type="number" border="bottom"
				@change="onChangeInputBasketNum"></u-input>
		</u-modal>

		<!-- 选择sku popup -->
		<u-popup :customStyle="{width: '90%', overflow: 'hidden'}" :show="skuPopup.show" mode="center" round="8"
			closeable @close="skuPopup.show = false">
			<view class="sku-container">
				<u-text :text="skuPopup.goods.name" bold size="18px"></u-text>
				<u-gap height="10px"></u-gap>
				<view v-for="(item,index) in skuPopup.goods.skus">
					<u-text :text="item.type" color="#999" size="13px"></u-text>
					<u-gap height="10px"></u-gap>
					<view class="u-flex u-flex-row-start">
						<Tag v-for="(el, idx) in item.children" :text="el.name" bold
							:type="el.active ? 'warning' : 'info'" :showDesc="el.price > 0" size="larger"
							@click="changeSku(item.children, idx)" style="margin-right: 5px;">
							<u-icon slot="desc" :name="el.mode === 1 ? 'rmb' : ''" color="#df3b35" labelColor="#df3b35"
								size="12px" labelSize="13px" space="0" :label="(el.mode === 2 && '+') + el.price" bold>
							</u-icon>
						</Tag>
					</view>
					<u-gap height="10px"></u-gap>
				</view>
			</view>
			<view class="sku-info-bar">
				<view class="u-flex u-flex-row-start wrap selected-sku">
					<u-text text="已选规格：" color="#666" size="12px"></u-text>
					<u-text :text="skuPopup.skuSelected" color="#333" size="12px"></u-text>
				</view>
				<view class="wrap u-flex sku-settlement-wrap">
					<view class="u-flex u-flex-row-start u-flex-col-end">
						<u-text text="总计" color="#000" size="13px" bold margin="0 8px 0 0"></u-text>
						<u-icon name="rmb" color="#ec5b50" bold labelColor="#ec5b50" size="12px" labelSize="28px"
							space="0" :label="skuPopup.totalPrice" class="sku-total-price"></u-icon>
					</view>
					<u-button icon="plus" size="mini" class="add-cart-btn" @click="addCart(1)"
						v-show="skuPopup.goods.skuBasketNum === 0">加入购物车</u-button>
					<Stepper v-show="skuPopup.goods.skuBasketNum > 0" :quantity="skuPopup.goods.skuBasketNum"
						@change="addCart" />
				</view>
			</view>
		</u-popup>

	</view>
</template>

<script>
	/**
	 * goodsList originGoodsList 是通过商品id分组 basketNum 统计此商品的加购数量
	 * basketList 购物车 通过商品id + sku分类 quantity 统计此商品的该sku下加购数量
	 */

	import FoodItem from '@/components/FoodItem.vue';
	import CommentItem from '@/components/CommentItem.vue';
	import Tag from '@/components/Tag.vue';
	import Stepper from '@/components/Stepper.vue';


	export default {
		data() {
			return {
				detail: {},
				categoryList: [],
				categoryActive: 0,
				// 所有商品数据
				originGoodsList: {},
				// 当前展示的上哦数据
				goodsList: [],
				sellerId: 0,
				basketList: [],
				isFav: false,
				menuActive: 0,
				menuList: [{
						name: '点单'
					},
					{
						name: '评价',
						badge: {
							value: 0,
						}
					},
					{
						name: '商家'
					}
				],
				evaluateList: [],
				// 购物车actionSheet
				basketActionSheet: {
					show: false,
					discount: {
						condition: 0,
						reduce: 0
					},
					packageFee: 0
				},
				// 输入框设置加购数量
				basketNumModal: {
					goodsId: '',
					show: false,
					quantity: 0
				},
				//选着sku规格栏
				skuPopup: {
					goods: {},
					show: false,
					skuSelected: '',
					totalPrice: 0
				},
				//统计栏
				statisticsBar: {
					totalPrice: 0,
					totalCount: 0,
					hasMandatory: false,
					canBuy: false
				},
				//定位信息
				location: {}
			}
		},
		components: {
			FoodItem,
			CommentItem,
			Tag,
			Stepper
		},
		methods: {
			//切换菜品分类
			changeCategory(index) {
				this.categoryActive = index
				let categoryId = this.categoryList[this.categoryActive].id
				let temp = this.originGoodsList[categoryId]
				if (temp) {
					this.goodsList = temp
				} else {
					this.$q({
						url: '/api/takeout/goods/list?sellerId=' + this.sellerId + '&categoryId=' + categoryId
					}).then(res => {
						this.goodsList = res.data.rows
						this.originGoodsList[categoryId] = this.goodsList

						this.updateStatisticsData()
					})
				}
			},
			//通过+-按钮修改加购数量
			onUpdateQuantity(id, quantity, skuIdGroup) {
				let basketIndex = this.basketList.findIndex(item => {
					let flag = true
					//都没有sku数据时不将此作为条件
					if (item.skuIdGroup || skuIdGroup) {
						flag = item.skuIdGroup === skuIdGroup
					}
					return item.goods.id === id && flag
				})
				//存在就修改数量 如果为0就删除
				if (basketIndex !== -1) {
					this.$set(this.basketList[basketIndex], 'quantity', quantity)
					if (quantity === 0) {
						this.basketList.splice(basketIndex, 1)
					}
					//不存在就添加
				} else {
					if (quantity > 0) {
						//添加时根据sku计算价格
						let goods = this.goodsList.find(item => item.id === id)
						if (goods.skus && skuIdGroup) {
							let skuIds = JSON.parse(skuIdGroup)
							//通过skuId组获取到对应所有sku对象 通过skuIds.indexOf 重复的id不会展示 item.children.find 同一个分类下只会查找一个 如果有分类需要多选 需要修改 todo
							let activeSkuArr = goods.skus.map(item => item.children.find(el => skuIds.indexOf(el.id) !== -
								1)).filter(item => item.price > 0)
							let baseSku = activeSkuArr.find(item => item.mode === 1)
							let totalPrice = 0
							if (baseSku) {
								totalPrice = baseSku.price
							} else {
								totalPrice = goods.price
							}
							totalPrice += activeSkuArr.filter(item => item.mode === 2).map(item => item.price).concat([0,
								0
							]).reduce((a, b) => a + b)
							goods.price = totalPrice
						}
						this.basketList.push({
							//模拟虚拟id 唯一即可
							id: uni.$u.guid(20),
							skuIdGroup: skuIdGroup,
							quantity: quantity,
							goods: goods
						})
					}
				}
				this.updateStatisticsData()

				this.$q({
					url: '/api/takeout/basket/update',
					method: 'POST',
					data: {
						goodsId: id,
						quantity: quantity,
						skuIdGroup: skuIdGroup
					},
					needToken: true,
				}).catch(async () => {
					console.log('加购失败');
					//如果修改失败重新拉取对应加购数量
					await this.init()
					this.updateStatisticsData()
				})

				//自动关闭购物袋显示
				if (this.basketActionSheet.show) {
					if (this.basketList.length === 0) {
						this.basketActionSheet.show = false
					}
				}
			},
			//点击加购的数字
			onClickBasketNum(goodsId, basketNum) {
				let goods = this.goodsList.find(item => item.id === goodsId)
				if (goods.skus.length === 0) {
					this.basketNumModal.goodsId = goodsId
					this.basketNumModal.show = true
					this.basketNumModal.quantity = basketNum
				}
			},
			//通过输入框修改加购数量
			onConfirmBasketNum() {
				this.onUpdateQuantity(this.basketNumModal.goodsId, parseInt(this.basketNumModal.quantity))
				this.basketNumModal.show = false
			},
			//输入框输入加购数量时
			onChangeInputBasketNum(e) {
				console.log(e);
				let goods = this.goodsList.find(item => item.id === this.basketNumModal.goodsId)
				if (goods) {
					if (e !== 0) {
						let temp = Math.max(goods.minPurchaseNum, e)
						if (goods.maxPurchaseNum) {
							temp = Math.min(temp, goods.maxPurchaseNum)
						}
						this.basketNumModal.quantity = temp
					}
				}
			},
			//点击选择规格
			onClickSku(id) {
				this.skuPopup.show = true
				let goods = this.goodsList.find(item => item.id === id)
				goods.skus.forEach(item => {
					item.children.forEach((el, index) => {
						el.active = index === 0
					})
				})
				this.skuPopup.goods = uni.$u.deepClone(goods)
				this.changeSku([], 0)
			},
			//切换规格
			changeSku(children, index) {
				// 对选择的方块设置选中效果
				children.forEach((item, idx) => {
					this.$set(item, 'active', index === idx)
				})

				//显示的规格显示文本
				this.skuPopup.skuSelected = this.skuPopup.goods.skus.map(item => item.children.find(el => el.active).name)
					.join('、')

				// 获取规格选择的总价格
				let totalPrice = 0
				let tempArr = this.skuPopup.goods.skus.map(item => item.children.find(el => el.active)).filter(item => item
					.price > 0)
				let baseSku = tempArr.find(item => item.mode === 1)
				//如果有sku基础价格则用sku价格 没有的话 使用商品价格
				if (baseSku) {
					totalPrice = baseSku.price
				} else {
					totalPrice = this.skuPopup.goods.price
				}
				totalPrice += tempArr.filter(item => item.mode === 2).map(item => item.price).concat([0, 0]).reduce((a,
					b) => a + b)
				this.skuPopup.totalPrice = totalPrice

				//显示不同sku的加购数量
				let skuIds = JSON.stringify(this.skuPopup.goods.skus.map(item => item.children.find(el => el.active).id))
				let thisGoodsSku = this.basketList.find(item => item.goods.id === this.skuPopup.goods.id && item
					.skuIdGroup === skuIds) || {
					quantity: 0
				}
				this.skuPopup.goods.skuBasketNum = thisGoodsSku.quantity

				this.$forceUpdate()
			},
			//通过选择规格加入购物车
			addCart(num) {
				let skuIds = JSON.stringify(this.skuPopup.goods.skus.map(item => item.children.find(el => el.active).id))
				this.skuPopup.goods.skuBasketNum = num
				this.onUpdateQuantity(this.skuPopup.goods.id, this.skuPopup.goods.skuBasketNum, skuIds)
			},
			//下单
			orderGoods() {
				uni.navigateTo({
					url: '/pages/seller/advancePayment'
				})
			},
			//清空购物车
			clearBasket() {
				 this.$q({
					url: '/api/takeout/basket/clear',
					method: 'DELETE',
					data: {
						sellerId: this.sellerId
					},
					needToken: true,
				}).then(res =>{
					this.basketActionSheet.show = false
					this.basketList = []
					this.updateStatisticsData()
				})
			},
			//更新统计数据
			updateStatisticsData() {
				//更新商品的加购数量
				Object.keys(this.originGoodsList).forEach(categoryId => {
					let goodsList = this.originGoodsList[categoryId]
					//更新单个商品加购数量
					goodsList.forEach(item => {
						let quantity = this.basketList.filter(el => el.goods.id === item.id).map(item =>
							item.quantity).concat([0, 0]).reduce((a, b) => a + b)
						this.$set(item, 'basketNum', quantity)
					})
				})

				//商品更新分类上面的总数
				let totalCount = 0
				this.categoryList.forEach(item => {
					let basketNum = this.basketList.filter(el => el.goods.categoryId === item.id).map(el => el
						.quantity).concat([0, 0]).reduce((a, b) => a + b)
					this.$set(item, 'basketNum', basketNum)
					totalCount += basketNum
				})
				this.statisticsBar.totalCount = totalCount


				//统计所有打包费
				this.basketActionSheet.packageFee = this.basketList.map(item => item.quantity * item.goods.packingFee)
					.concat([0, 0]).reduce((a, b) => a + b)
				//统计总金额
				this.statisticsBar.totalPrice = this.basketList.map(item => item.quantity * item.goods.price).concat([0,
					0
				]).reduce((a, b) => a + b) + this.basketActionSheet.packageFee

				//更新是否能够购买状态
				this.statisticsBar.hasMandatory = this.basketList.find(item => item.goods.isMandatory && item.quantity >
					0) !== undefined
				//是否可以购买 有必点品切大于起送金额
				this.statisticsBar.canBuy = this.statisticsBar.hasMandatory && this.statisticsBar.totalPrice >= this.detail
					.minDeliveryPrice

				//设置满减
				let activities = this.detail.activities.filter(item => this.statisticsBar.totalPrice > item.condition)
				activities.forEach(item => {
					// 红包减免
					if (item.type === 1) {
						item.finalPrice = this.statisticsBar.totalPrice - item.reduce
					} else if (item.type === 2) {
						item.finalPrice = this.statisticsBar.totalPrice * item.reduce
					}
				})
				activities = activities.sort((a, b) => a.finalPrice - b.finalPrice)
				if (activities.length > 0) {
					if (this.basketActionSheet.discount.id !== activities[0].id) {
						uni.showToast({
							icon: 'none',
							title: '已享' + this.discountStr(activities[0], 1)
						})
					}
					this.basketActionSheet.discount = activities[0]
				}else{
					this.basketActionSheet.discount = {}
				}

			},
			//将购物车数据解析成FootItem所需数据
			formatBasketGoods(basket) {
				let descriptionArr = []
				if (basket.skuIdGroup) {
					let skuIds = JSON.parse(basket.skuIdGroup)
					// 通过skuIds.indexOf 重复的id不会展示 item.children.find 同一个分类下只会查找一个
					descriptionArr = basket.goods.skus.map(item => item.children.find(el => skuIds.indexOf(el.id) !== -1))
						.map(item => item.name)
				}
				return Object.assign(uni.$u.deepClone(basket.goods), {
					basketNum: basket.quantity,
					skus: [],
					description: descriptionArr.join('、'),
					score: null,
					salesVolume: null,
					skuIdGroup: basket.skuIdGroup
				})
			},
			// 获取对应折扣的提示词
			discountStr(discount, mode) {
				// mode 1 详细提示 2 简略提示
				if (discount.type === 1) {
					return mode === 1 ? ('满' + discount.condition + '元减' + discount.reduce + '元') : (discount.condition +
						'减' + discount.reduce)
				} else if (discount.type === 2) {
					return mode === 1 ? ('满' + discount.condition + '元打' + parseInt(discount.reduce * 100) + '折') : ('满' +
						discount.condition + '元' + parseInt(discount.reduce * 100) + '折')
				}
			},
			//收藏店铺 / 取消收藏店铺
			collectSeller() {
				this.$q({
					url: '/api/takeout/collection/' + (this.isFav ? 'del' : 'add'),
					data: {
						sellerId: this.sellerId
					},
					method: this.isFav ? 'DELETE' : 'PUT',
					needToken: true
				}).then(res =>{
					this.isFav = !this.isFav
				}).catch(err =>{
					this.updateFavStatus()
				})
			},
			// 更新收藏状态
			updateFavStatus(){
				this.$q({
					url: '/api/takeout/collection/check',
					data: {
						sellerId: this.sellerId
					},
					needToken: true
				}).then(res =>{
					this.isFav = res.data.check
				})
			},
			//初始化
			async init() {

				let res = await Promise.all([
					//查询店铺详情
					this.$q({
						url: '/api/takeout/seller/detail/' + this.sellerId,
						data: {
							myLocation: encodeURI([this.location.latitude, this.location.longitude].join(
								','))
						}
					}),
					//查询购物车数据
					this.$q({
						url: '/api/takeout/basket/listBySeller?sellerId=' + this.sellerId,
						needToken: true,
					}),
					//查询商品分类数据
					this.$q({
						url: '/api/takeout/goods/category/list?sellerId=' + this.sellerId
					})
				])

				this.detail = res[0].data

				if (res[1].data && res[1].data.rows) {
					//过滤有效商品
					this.basketList = res[1].data.rows.filter(item => item.skuValid && item.goods.status === 1)
				}

				this.categoryList = res[2].data.rows.sort((a, b) => a.name.length - b.name.length)
				this.changeCategory(this.categoryActive)

			}
		},
		onLoad(options) {
			this.sellerId = parseInt(options.id)
			uni.getLocation({
				type: 'wgs84',
				geocode: true,
				success: (res) => {
					this.location = res
					this.init()
				},
				fail: (e) => {
					console.log(e);
					uni.showToast({
						title: '获取的定位信息失败',
						icon: 'none'
					})
				}
			})
			this.updateFavStatus()
		}
	}
</script>

<style lang="scss">
	.seller-cover {
		position: relative;
		width: 100%;
		height: 180px;
		background-size: cover !important;
		background-repeat: no-repeat !important;
		background-position: center top !important;
	}


	.seller-body {
		border-top-left-radius: 8px;
		border-top-right-radius: 8px;
		background-color: #fff;
		transform: translateY(-30px);

		//清除分割线间距
		::v-deep .u-divider {
			margin-bottom: 0 !important;
		}

		.seller-info-wrap {
			width: calc(100% - 120px);

			.seller-name {
				font-size: 20px;
				font-weight: 500;
			}

			.seller-detail-info-wrap {
				display: flex;
				align-items: center;
				justify-content: flex-start;

				.u-text {
					flex: unset;
					margin-left: 10px !important;
				}
			}

			.activities-wrap {
				flex-wrap: wrap;

				>view {
					margin-right: 5px;
					margin-bottom: 5px;
				}
			}

			.coupons-wrap {
				flex-wrap: wrap;
				transform-origin: left top;
				transform: scale(.7);

				::v-deep .u-tag {
					margin-right: 5px !important;
					margin-bottom: 5px;
				}
			}
		}

		.seller-avatar {
			width: 80px;
			height: 80px;
			transform: translateY(-20px);
			background-color: #fff;
			border-radius: 5px;
		}

		.goods-container {
			width: 100%;
			height: 500px;
			overflow: hidden auto;
			display: flex;
			align-items: flex-start;
			justify-content: space-between;

			.category-wrap {
				width: 80px;
				height: 100%;
				display: flex;
				flex-direction: column;
				background-color: #f2f3f6;
				padding-bottom: 60px;
				overflow: hidden;

				>view {
					display: flex;
					align-items: center;
					justify-content: center;
					width: 100%;
					min-height: 40px;
					color: #333;
					font-size: 12px;
					transition: .3s;
					position: relative;
					padding: 6px 0;

					>.category-name {
						display: block;
						width: 65%;
						margin: 0 auto;
					}
				}

				>view.active {
					background-color: #fff;
					font-weight: bold;
				}

			}

			.goods-wrap {
				width: calc(100% - 80px);
				height: 100%;
			}
		}

		.evaluate-wrap {
			box-sizing: border-box;
			padding: 0 8px;
		}

		::v-deep .u-rate .uicon-star-fill {
			color: #f7df62 !important;
		}

		.seller-reply-wrap {
			background-color: #eee;
			width: 95%;
			margin: 0 auto;
			border-radius: 5px;
			box-sizing: border-box;
			padding: 4px 10px;

			h3 {
				font-size: 14px;
				color: #333;
				font-weight: 400;
			}
		}


	}

	.statistics-bar {
		position: fixed;
		left: 50%;
		transform: translateX(-50%);
		bottom: 2%;
		width: 90%;
		height: 50px;
		border-radius: 25px;
		background-color: #333;
		display: flex;
		align-items: center;
		justify-content: space-between;
		overflow: hidden;
		padding-left: 20px;
		z-index: 99999;


		.statistics-bar-right-info-wrap {
			width: 120px;
			height: 50px !important;
			margin: 0;
		}

		.purchase-tips-wrap {
			width: 100%;
			height: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
			flex-direction: column;
			box-sizing: border-box;
			padding: 5px 0;
		}

		.purchase-btn {
			width: 100%;
			height: 100%;
		}

		.basket-icon-wrap {
			display: flex;
			align-items: center;
			justify-content: space-between;
		}
	}

	.basket-goods-wrap {
		max-height: 60vh;
	}

	.sku-container {
		width: 100%;
		box-sizing: border-box;
		padding: 10px 15px;
	}

	.selected-sku {
		background-color: #fafafa;
		padding: 6px 10px;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
	}

	.sku-settlement-wrap {
		padding: 10px;

		.sku-total-price {
			align-items: flex-end;

			::v-deep .u-icon__icon {
				line-height: unset !important;
			}
		}

		.add-cart-btn {
			background-color: $main-color;
			width: 100px;
			padding: 15px 0;
			border-radius: 5px;
			margin: 0;

			::v-deep .u-icon__icon.uicon-plus {
				line-height: unset !important;
				font-size: 12px !important;
			}
		}
	}

	.seller-detail {
		::v-deep .u-popup__content {
			overflow: hidden;
		}
	}

	.discount-tip-wrap {
		justify-content: center;
		padding: 8px 0;
		background-color: #f2eed7;
	}
</style>
