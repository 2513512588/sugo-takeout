<template>
	<view class="pay-wrap">
		<view class="order-info">
			<u-gap height="8"></u-gap>
			<u-icon name="rmb" :label="order.orderInfo.total" size="25" labelSize="50" color="#000" labelColor="#000"
				bold>
			</u-icon>
			<u-gap height="8"></u-gap>
			<u-text :text="order.sellerInfo.name" color="#999" size="13"></u-text>
		</view>
		<view class="card">
			<u-cell-group :border="false">
				<u-radio-group v-model="payType" placement="column">
					<u-cell :border="false" name="alipay" @click="onChangeCell">
						<u-icon slot="title" name="zhifubao-circle-fill" label="支付宝支付" size="40"
							color="rgb(41, 121, 255)" space="10"></u-icon>
						<u-radio name="alipay" activeColor="#f29100" slot="value" v-if="update"></u-radio>
					</u-cell>
					<u-cell :border="false" name="wechat" @click="onChangeCell">
						<u-icon slot="title" name="weixin-circle-fill" label="微信支付" size="40" color="green" space="10">
						</u-icon>
						<u-radio name="wechat" activeColor="#f29100" slot="value" v-if="update"></u-radio>
					</u-cell>
					<u-cell :border="false" name="apple-wallet" @click="onChangeCell">
						<u-icon slot="title" name="apple-fill" label="Apple钱包" size="40" color="#000" space="10">
						</u-icon>
						<u-radio name="apple-wallet" activeColor="#f29100" slot="value" v-if="update"></u-radio>
					</u-cell>
				</u-radio-group>
			</u-cell-group>
		</view>

		<u-popup :show="show" mode="center" round>
			<view class="password-input-wrap">
				<view class="cell" v-for="(item,index) in 6" v-text="password.length > index ? '*' : ''"></view>
			</view>
		</u-popup>

		<u-keyboard :show="show" confirmText="生物认证" @confirm="biological" @cancel="cancelKeyBoard" @change="valChange"
			@backspace="backspace" tips="输入支付密码" mode="number">

			<!-- <u-code-input v-model="password" :maxlength="6" dot></u-code-input> -->
			<!-- <view class="password-input-wrap"> -->
			<!-- <u-code-input v-model="password" :maxlength="6" dot></u-code-input> -->
			<!-- <view class="cell" v-for="(item,index) in 6" v-text="password.length > index ? '*' : ''"></view> -->
			<!-- </view> -->
		</u-keyboard>

		<u-button type="warning" class="pay-btn" @click="alipay">确定支付</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderNo: '',
				order: {
					orderInfo: {},
					sellerInfo: {}
				},
				payType: 'alipay',
				update: true,
				show: false,
				password: '',
				// 支付类型 1外卖 2电影票
				type: 1,
				payInfo: ''
			}
		},
		methods: {
			alipay() {
				//#ifdef APP
					uni.requestPayment({
						provider: 'alipay',
						orderInfo: this.payInfo, //微信、支付宝订单数据 【注意微信的订单信息，键值应该全部是小写，不能采用驼峰命名】
						success: function(res) {
							console.log('success:' + JSON.stringify(res));
							uni.redirectTo({
								url: '/pages/order/order'
							})
						},
						fail: function(err) {
							console.log('fail:' + JSON.stringify(err));
						}
					})
				//#endif
				
				//#ifdef H5
					location.href = this.payInfo
				//#endif
			},
			pay() {
				uni.showLoading({
					title: '支付中...'
				})
				if (this.type == 1) {
					this.doResp(this.$q({
						url: '/prod-api/api/takeout/pay',
						token: true,
						method: 'POST',
						type: 'json',
						data: {
							orderNo: this.orderNo,
							paymentType: this.payType
						},
						noLoading: true
					}))
				} else if (this.type == 2) {
					this.doResp(this.$q({
						url: '/prod-api/api/movie/ticket/order/pay/' + this.orderNo,
						data: {
							paymentType: this.payType
						},
						token: true
					}))
				}
			},
			doResp(promise) {
				promise.then(() => {
					setTimeout(() => {
						uni.hideLoading()
						uni.showToast({
							title: '下单成功',
							duration: 2000,
							icon: 'success'
						})
						setTimeout(() => {
							uni.redirectTo({
								url: '/pages/order/order'
							})
						}, 1500)
					}, 1000)
				}).catch(() => {
					this.password = ''
				})
			},
			// 按键被点击(点击退格键不会触发此事件)
			valChange(val) {
				// 将每次按键的值拼接到value变量中，注意+=写法
				if (this.show) {
					this.password += val;
					this.password = this.password.substring(0, Math.min(this.password.length, 6))
					setTimeout(() => {
						if (this.password.length === 6) {
							let pwd = uni.getStorageSync('pay-pwd') || '123456'
							if (this.password === pwd) {
								this.pay()
							} else {
								this.password = ''
								uni.showToast({
									icon: 'error',
									duration: 2000,
									title: '密码错误'
								})
							}
							this.show = false
						}
					}, 300)
				}
			},
			// 退格键被点击
			backspace() {
				// 删除value的最后一个字符
				if (this.password.length) this.password = this.password.substr(0, this.password.length - 1);

			},
			cancelKeyBoard() {
				this.show = false
				this.password = ''
			},
			onChangeCell(e) {
				this.payType = e.name
				this.update = false
				this.$nextTick(() => {
					this.update = true
				})
			},
			biological() {
				uni.checkIsSupportSoterAuthentication({
					success: (e) => {
						if (e.supportMode.indexOf('facial') !== -1) {
							this.show = false
							uni.startSoterAuthentication({
								requestAuthModes: ['facial'],
								//挑战因子 主要存储订单号
								challenge: '123456',
								authContent: '请用FaceID解锁',
								success: (res) => {
									console.log(res);
									this.pay()
								},
								fail(err) {
									console.log(err);
								},
								complete(res) {
									console.log(res);
								}
							})
						} else if (e.supportMode.indexOf('fingerPrint') !== -1) {
							this.show = false
							uni.startSoterAuthentication({
								requestAuthModes: ['fingerPrint'],
								challenge: '123456',
								authContent: '请用指纹解锁',
								success: (res) => {
									console.log(res);
									this.pay()
								},
								fail(err) {
									console.log(err);
								},
								complete(res) {
									console.log(res);
								}
							})
						} else {
							uni.showToast({
								icon: 'none',
								title: '你的设备暂不支持',
								duration: 2000
							})
						}
					},
					fail: () => {
						uni.showToast({
							icon: 'none',
							title: '你的设备暂不支持',
							duration: 2000
						})
					}
				})
			}
		},
		onLoad(options) {
			//#ifdef APP 
			var EnvUtils = plus.android.importClass("com.alipay.sdk.app.EnvUtils");
			EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
			//#endif
			
			this.type = options.type
			this.orderNo = options.orderNo
			this.$q({
				url: '/api/takeout/order/payment/detail/' + this.orderNo,
				needToken: true
			}).then(res => {
				this.order.orderInfo = res.data.order
				//#ifdef APP 
					this.payInfo = res.data.payInfo
				//#endif
				//#ifdef H5
					this.payInfo = res.data.payURL
				//#endif
			})
		}
	}
</script>

<style lang="scss">
	.pay-wrap {
		::v-depp .u-popup--content u-popup--content--round-center {
			position: absolute !important;
			top: 35% !important;
		}
	}

	.order-info {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		width: 100%;
		padding: 20px 0;
	}

	.card {
		width: 95%;
		margin: 0 auto;
		box-shadow: 0 0 5px #eee;
		border: 1px solid #eee;
		border-radius: 5px;
	}

	.pay-btn {
		width: 95% !important;
		margin: 0 auto;
		margin-top: 50px;
	}

	.password-input-wrap {
		padding: 5px;
		width: 310px;
		height: 60px;
		display: flex;
		align-items: center;
		justify-content: center;

		.cell {
			width: 40px;
			height: 40px;
			border: 1px solid #eee;
			display: flex;
			// align-items: center;
			justify-content: center;
			margin: 0 6px;
			// background-color: #E6E6E6;
			box-sizing: border-box;
			font-size: 30px;
			font-weight: bold;
			color: #000;
		}
	}
</style>
