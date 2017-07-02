<template>
    <div id="admin">
        {{msg}}
    </div>
</template>

<script>
    import Global from "./Global.vue"
    export default {
        name:"Admin",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = Global.userInfo;
        }
    }
</script>
