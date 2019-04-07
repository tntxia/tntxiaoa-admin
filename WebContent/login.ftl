
<div class="logo"></div>
<div id="main-content">
	<header><h1>OA管理-登录</h1></header>
	<section>
		<div class="input-row">
			<input class="username" v-model="username" placeholder="用户名" value="" spellcheck="false" autofocus="" required>
		</div>
		<div class="input-row">
			<input class="password" type="password" v-model="password" placeholder="密码" value="" spellcheck="false" autofocus="" required>
		</div>
		<div class="button-row">
			<button @click="login">登陆 </button>
		</div>
	</section>
</div>

