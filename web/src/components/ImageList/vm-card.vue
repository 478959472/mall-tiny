<template>
  <div :class="[type === 'horizantal' ? 'vm-card-horizantal' : 'vm-card-vertical' , 'vm-panel']">
    <div class="card-img">
<!--      <img :src="img" alt="">-->
      <video :src="video"  controls="controls" type="video/mp4"></video>
      <div v-if="editable && type == 'vertical'" class="control">
        <span class="edit">
          <a :href="editUrl">
            <i class="fa fa-pencil"></i>
          </a>
        </span>
        <span class="delete">
          <i class="fa fa-trash" @click="modalDelete=true"></i>
        </span>
      </div>
    </div>
    <div class="card-desc panel-body">
      <h2>{{ title }}</h2>
      <p>{{ desc }}</p>
    </div>
    <Modal
        v-model="modalDelete"
        title="Delete"
        ok-text="OK"
        cancel-text="Cancel"
        v-on:on-ok="deleteOk">
        Are you sure to delete this data?
    </Modal>
  </div>
</template>
<script>
  export default {
    name: 'VmCard',
    props: {
      type: {
        type: String,
        default: 'vertical'
      },
      editable: {
        type: Boolean,
        default: false
      },
      title: {
        type: String,
        default: 'Title'
      },
      img: {
        type: String,
        default: require('@/assets/images/img-1.jpg')
      },
      video: {
        type: String,
        default: ''
      },
      desc: {
        type: String,
        default: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry,Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s'
      },
      detailUrl: {
        type: String,
        default: '#'
      },
      editUrl: {
        type: String,
        default: '#'
      }
    },
    data: function () {
      return {
        modalDelete: false
      }
    },
    methods: {
      deleteOk: function () {
        this.$emit('delete-ok')
      }
    }
  }
</script>
