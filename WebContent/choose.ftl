
<div id="vue">
请选择一个应用 
<select v-model="app">
<option value="">请选择</option>
<option v-for="a in appList" :value="a.id">{{a.name }}</option>
</select>
<button @click="confirm()">确定</button>
<button @click="appManage()">应用管理</button>
</div>

