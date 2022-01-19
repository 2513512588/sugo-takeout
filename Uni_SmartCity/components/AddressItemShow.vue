<template>
	<u-cell isLink :border="false">
		<view class="address_item" slot="title">
			<h2 class="address_detail">{{data.addressDetail}}</h2>
			<u-gap height="3"></u-gap>
			<view class="info_wrap">
				<p>
					<u-text color="#999" :size="13" :text="data.name"></u-text>
				</p>
				<p>
					<u-text color="#999" :size="13" :text="data.phone"></u-text>
				</p>
			</view>
			<u-gap height="5"></u-gap>
			<view class="tags_group">
				<u-tag v-if="data.label !== ''" :text="data.label" type="warning"></u-tag>
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
	.info_wrap {
		display: flex;
		align-items: center;
		justify-content: flex-start;

		p {
			margin-right: 8px;
		}
	}

	.address_detail {
		font-size: 20px;
		font-weight: 500;
		color: #333;
	}
</style>
