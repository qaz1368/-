<template>
  <div class="video-player">
    <video
        :src="processedSrc"
        controls
        preload="metadata"
        class="video-element"
    >
      Your browser does not support the video tag.
    </video>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  src: {
    type: String,
    required: true
  }
})

const processedSrc = computed(() => {
  if (props.src.startsWith('http://') || props.src.startsWith('https://')) {
    return props.src;
  } else {
    return `${import.meta.env.VITE_APP_UPLOAD_URL}${props.src}`;
  }
})
</script>

<style scoped>.video-player {
  width: 500px; /* 固定宽度 */
  height: 400px; /* 固定高度 */
  margin: 0 auto;
  padding: 0;
}

.video-element {
  width: 100%; /* 宽度占满 .video-player */
  height: 100%; /* 高度占满 .video-player */
  object-fit: cover; /* 确保视频内容填充整个容器 */
}
</style>

