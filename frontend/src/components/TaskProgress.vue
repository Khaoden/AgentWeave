<template>
  <section>
    <h2>Task progress</h2>
    <ul v-if="tasks?.length">
      <li v-for="task in tasks" :key="task.id">
        <strong>Task #{{ task.id }}</strong> — {{ task.type }} — <em>{{ task.status }}</em>
      </li>
    </ul>
    <p v-else>No tasks yet.</p>
  </section>
</template>

<script setup>
defineProps({
  tasks: {
    type: Array,
    default: () => []
  }
})
</script>
