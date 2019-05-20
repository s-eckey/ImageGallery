<template>
  <div id="imageGalleryItem" class="imageGalleryItem">
    <transition name="fade">
      <img class="image" v-if="show" :src="img">
    </transition>
  </div>
</template>

<script>
import axios from "axios";
import { setTimeout } from "timers";
const uuidv1 = require("uuid/v1");

export default {
  name: "imageGalleryItem",
  data: function() {
    return {
      img: String,
      show: false
    };
  },
  created() {
    this.loadNextImage();
    axios.get(`rest/configuration/viewTime?uuid=${uuidv1()}`).then(resp => {
      if (resp.data) {
        let viewTime = resp.data;
        setInterval(() => {
          this.loadNextImage();
        }, viewTime);
      }
    });
  },
  methods: {
    loadNextImage: function() {
      axios.get(`rest/image/next?uuid=${uuidv1()}`).then(resp => {
        if (resp.data) {
          this.$data.show = false;
          this.$data.img =
            "data:image/" +
            resp.data.fileEnding +
            ";base64," +
            resp.data.imageData;
          setTimeout(() => {
            this.$data.show = true;
          }, 1300);
        }
      });
    }
  }
};
</script>
<style>
.image {
  max-height: 49vh;
  max-width: 49vw;
  width: auto;
  height: auto;
  display: block;
  margin: auto;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
