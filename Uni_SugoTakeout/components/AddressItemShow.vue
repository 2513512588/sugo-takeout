<template>
	<u-cell isLink :border="false">
		<view class="address-item" slot="title">
			<h2 class="address-detail">{{data.name}}</h2>
			<u-gap height="3"></u-gap>
			<view class="u-flex u-flex-row-start info-cell">
				<p>
					<u-text color="#999" :size="13" :text="data.consignee"></u-text>
				</p>
				<p>
					<u-text color="#999" :size="13" :text="data.phone"></u-text>
				</p>
			</view>
			<u-gap height="5"></u-gap>
			<view class="u-flex">
				<u-tag v-if="data.marker" :text="data.marker" type="warning"></u-tag>
			</view>
		</view>

		<view slot="right-icon">
			<slot>
				<u-icon :name="rightIcon"></u-icon>
			</slot>
		</view>

	</u-cell>
</template>

<script>
	export default {
		name: "AddressItemShow",
		props: {
			data: {
				type: Object
			},
			rightIcon: {
				type: String,
				default: 'arrow-right'
			}
		},
		data() {
			return {

			};
		}
	}
</script>

<style lang="scss">
	.info-cell{
		p{
			margin-right: 8px;
		}
	}
	.address-detail {
		font-size: 20px;
		font-weight: 500;
		color: #333;
		text-align: left;
	}
</style>
