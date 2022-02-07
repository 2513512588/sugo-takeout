<template>
	<view class="address-container">
		<geolocation @ready="mapReady" class="map" :currentPosition="location.currentPosition"></geolocation>
		<view class="form-wrap">
			<view class="card u-flex wrap" v-show="Object.keys(location).length > 0">
				<view class="address-detail-wrap">
					<u-text :text="location.name" bold size="13px" color="#000" lines="1"></u-text>
					<u-text :text="location.detail" bold size="12px" color="#999" lines="1"></u-text>
				</view>
				<u-button plain type="warning" size="mini" class="modify-addr-btn" @click="chooseLocation">修改地址</u-button>
			</view>
			<u-gap height="8px"></u-gap>
			<view class="wrap card address-form-wrap">
				<u-button type="warning" plain @click="chooseLocation" v-show="Object.keys(location).length === 0">
					<u-icon name="arrow-right" label="选择收货地址" labelPos="left" labelColor="#e05e37" color="#e05e37" bold ></u-icon>
				</u-button>
				<u-gap height="5px"></u-gap>
				<u--form :model="address" labelWidth="60" :rules="rules" ref="uForm" errorType="toast">
					<u-form-item label="门牌号" prop="houseNumber">
						<u-input placeholder="详细地址, 例如1层108室" v-model="address.houseNumber" border="bottom"></u-input>
					</u-form-item>
					<u-form-item label="标签" >
						<view class="u-flex choose_tag_wrap">
							<u-tag text="家" type="info" @click="address.marker = '家'" :plain="address.marker !== '家'"></u-tag>
							<u-tag text="公司" type="info" @click="address.marker = '公司'" :plain="address.marker !== '公司'"></u-tag>
							<u-tag text="学校" type="info" @click="address.marker = '学校'" :plain="address.marker !== '学校'"></u-tag>
						</view>
					</u-form-item>
					<u-form-item label="联系人" prop="consignee">
						<u-input v-model="address.consignee" placeholder="请填写收货人姓名" border="bottom">
							<u-radio-group slot="suffix" activeColor="#FFD000" v-model="address.sex">
								<u-radio label="先生" :name="1" ></u-radio>
								<u-radio label="女士" style="margin-left: 10px;" :name="2"></u-radio>
							</u-radio-group>
						</u-input>
					</u-form-item>
					<u-form-item label="手机号" prop="phone">
						<u-input v-model="address.phone" type="number" placeholder="请填写收货手机号码" border="bottom"></u-input>
					</u-form-item>
					<u-form-item>
						<u-button type="warning" @click="save" class="u-flex" color="linear-gradient(to right, #ffe91b, #FFD000">
							<u-text text="保存地址" bold color="#000" ></u-text>
						</u-button>
					</u-form-item>
				</u--form>
			</view>
		</view>
	</view>
</template>

<script>
	import geolocation from '@/components/ITkoala-amap/geolocation.vue'

	export default {
		components: {
			geolocation
		},
		data() {
			return {
				addrId: '',
				address: {
					houseNumber: '',
					consignee: '',
					phone: '',
					marker: '',
					sex: 1,
				},
				location: {

				},
				rules: {
					houseNumber: {
						type: 'string',
						trigger: ['change', 'blur'],
						message: '门牌号不能为空',
						required: true
					},
					consignee: {
						type: 'string',
						trigger: ['change', 'blur'],
						message: '姓名不能为空',
						required: true
					},
					phone: {
						type: 'string',
						trigger: ['change', 'blur'],
						message: '手机号格式不正确',
						required: true,
						validator: (rule, value, callback) => {
							// 上面有说，返回true表示校验通过，返回false表示不通过
							// uni.$u.test.mobile()就是返回true或者false的
							return uni.$u.test.mobile(value);
						},
					}
				}
			}
		},
		methods: {
			save() {
				if(Object.keys(this.location).length > 0){
					this.$refs.uForm.validate().then(() => {
						this.$q({
							url: '/api/takeout/address/add',
							method: this.address.id ? 'PUT' : 'POST',
							data: Object.assign(this.address, this.location.currentPosition, {
								name: this.location.name,
								address: this.location.detail,
							}),
							needToken: true
						}).then(res => {
							uni.showToast({
								icon: 'success',
								title: '保存成功'
							})
							uni.navigateBack()
						})
					})
				}else {
					uni.showToast({
						icon: 'none',
						title: '请选择收货地址'
					})
				}
			},
			deleteAddress() {
				if (this.address.id) {
					uni.showModal({
						title: '提示',
						content: '是否确定删除',
						success: (res) => {
							if (res.confirm) {
								this.$q({
									url: '/api/takeout/address/del' + this.address.id,
									method: 'DELETE',
									needToken: true
								}).then(() => {
									uni.showToast({
										title: '删除成功',
										icon: 'success',
									})
									uni.navigateBack()
								})
							}
						}
					})
				}
			},
			chooseLocation(){
				uni.chooseLocation({
					success: (e) => {
						this.location = {
							name: e.name,
							detail: e.address,
							currentPosition: {
								lat: e.latitude,
								lng: e.longitude
							}
						}
					},
					complete: (e) => {
						console.log(e);
					}
				})
			},
			mapReady(){
				if(this.addrId){
					if (this.addrId) {
						this.$q({
							url: '/api/takeout/address/detail/' + this.addrId,
							needToken: true,
						}).then(res => {
							this.address = res.data
							this.location = {
								name: this.address.name,
								detail: this.address.address,
								currentPosition: {
									lat: this.address.lat,
									lng: this.address.lng
								}
							}
						})
					}
				}
			}
		},
		onLoad(options) {
			this.addrId = options.id
		},
		onNavigationBarButtonTap() {
			
		},
	}
</script>

<style lang="scss">
	.address-container{
		position: fixed;
		width: 100%;
		height: 100vh;
		left: 0;
		top: 0;
		
		::v-deep .u-border-bottom{
			border-color: #dadbde80 !important;
		}
	}
	
	.choose_tag_wrap {
		::v-deep .u-tag {
			margin-right: 7px !important;
		}
	}

	.map {
		width: 100%;
		height: 100vh;
		position: absolute;
		z-index: 0;
	}

	.card {
		width: 95vw;
		padding: 10px 15px;
	}
	
	.form-wrap{
		height: calc(100vh - 120px);
		transform: translateY(120px);
		position: relative;
		z-index: 99;
		
		.address-form-wrap{
			height: calc(100vh - 120px);
		}
		
		.address-detail-wrap{
			width: calc(100% - 90px);
		}
		
		.modify-addr-btn{
			width: 80px;
			padding: 15px 0 !important;
			border-radius: 6px;
			font-size: 13px;
			font-weight: bold;
			margin: 0;
		}
	}
	
	
</style>
