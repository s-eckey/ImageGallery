<template>
  <div id="imageGalleryItem" class="imageGalleryItem">
    <transition name="fade">
      <img
        class="image"
        v-if="show"
        :src="img"
        alt="Bilder per Mail an meinehochzeit@gmail.com senden!"
      >
    </transition>
  </div>
</template>

<script>
import axios from "axios";
import { setTimeout } from "timers";

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
    axios
      .get(`rest/configuration/viewTime?timestamp=${new Date().getTime()}`)
      .then(resp => {
        if (resp.data) {
          let viewTime = resp.data;
          setInterval(() => {
            this.loadNextImage();
          }, viewTime);
        }
      })
      .catch(err => {
        alert("Backend can not be reached");
      });
  },
  methods: {
    loadNextImage: function() {
      axios.get("rest/image/next").then(resp => {
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
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
