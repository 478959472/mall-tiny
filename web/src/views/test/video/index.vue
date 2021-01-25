<template>
  <VmImageList :data="dataImageList" @delete-ok="deletefn" @search-list="searchList" @add-one="addVideo" class="vm-margin"></VmImageList>
</template>

<script>
import VmImageList from '@/components/ImageList'
import {listMyVideo} from '@/api/video';

export default {
  name: 'ImageList',
  components: {
    VmImageList
  },
  methods: {
    deletefn: function (data) {
      for (let i = 0; i < this.dataImageList.length; i++) {
        if (this.dataImageList[i].id === data.id) {
          this.dataImageList.splice(i, 1)
        }
      }
    },
    addVideo:function () {
      console.log("新建")
      window.open("http://10.10.215.109:8033/tool?direction=1",'_blank')

    }
    ,
    searchList: function (key) {
      console.log("搜索:" + key)
      this.dataImageList = []
      listMyVideo().then(response => {
        let videoList = response.data;
        videoList.forEach(item => {
          let video = {
            id: item.id,
            title: item.title,
            img: item.thumbnailUrl,
            video: item.url,
            desc: item.description,
            detailUrl: '#',
            editUrl: '#'
          };
          this.dataImageList.push(video)
        });
      });
    }
  },
  created() {
    //自动加载indexs方法
    this.searchList();
  },
  data: function () {
    return {
      dataImageList: [
        {
          id: '201707101552',
          title: 'Title1',
          img: require('@/assets/images/img-1.jpg'),
          video: require('@/assets/video/v-1.mp4'),
          desc: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry,Lorem Ipsum has been the industry\'s standard dummy text ever sincc the 1500s ly dummy tly dummy tly dummy tly dummy tly dummy tly dummy t',
          detailUrl: '#',
          editUrl: '#'
        }
      ]
    }
  }
}
</script>
