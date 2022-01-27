<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "@/uni_modules/uview-ui/index.scss";

	
	html,
	body {
		background-color: #fff;
	}

	.card {
		padding: 10px 0;
		box-sizing: border-box;
		background-color: #fff;
		width: 95%;
		margin: 0 auto;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
		border: 1px solid rgba(0, 0, 0, .06);
		border-radius: 5px;
		overflow: hidden;
	}

	.wrap {
		box-sizing: border-box;
		width: 100%;
		padding: 0 10px;
	}

	.u-flex {
		display: flex;
		align-items: center;
		justify-content: space-between;
		
		.u-text{
			flex: unset !important;
		}
	}
	.u-flex-start{
		align-items: flex-start;
		justify-content: flex-start;
	}
	.u-flex-col-start{
		align-items: flex-start;
	}
	.u-flex-row-start{
		justify-content: flex-start;
	}
	.u-flex-end{
		align-items: flex-end;
		justify-content: flex-end;
	}
	.u-flex-col-end{
		align-items: flex-end;
	}
	.u-flex-row-end{
		justify-content: flex-end !important;
	}
	
	.u-line-1{
		display: block !important;
	}
	
</style>
