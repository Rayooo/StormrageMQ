<template>
    <div id="message">
        {{msg}}
    </div>
</template>

<script>
    import Global from "../Global.vue"
    export default {
        name:"Message",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = "这是消息列表";
        }
    }
</script>
