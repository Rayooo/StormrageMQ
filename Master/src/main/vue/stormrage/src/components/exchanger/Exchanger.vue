<template>
    <div id="exchanger">
        {{msg}}
    </div>
</template>

<script>
    import Global from "../Global.vue"
    export default {
        name:"Exchanger",
        data() {
            return {
                msg: ""
            }
        },
        mounted() {
            this.msg = "这是交换器";
        }
    }
</script>
