<template>
  <section>
    <h2>Report</h2>
    <div v-if="taskResult">
      <p><strong>Task:</strong> {{ taskResult.taskId }}</p>
      <p>{{ taskResult.result }}</p>
    </div>
    <p v-else>Waiting for agent output.</p>
  </section>
</template>

<script setup>
defineProps({
  taskResult: {
    type: Object,
    default: null
  }
})
</script>
