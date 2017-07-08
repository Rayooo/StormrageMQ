<template>
    <div id="queue">
        {{msg}}
    </div>
</template>

<script>
    import Global from "../Global.vue"
    export default {
        name:"Queue",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = "这是队列";
        }
    }
</script>
