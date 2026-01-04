const API_BASE_URL = '/api/tasks';
let editingTaskId = null;

// Fetch and display all tasks
async function fetchTasks() {
    const container = document.getElementById('tasksContainer');

    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const page = await response.json();
        const tasks = page.content;

        if (!Array.isArray(tasks) || tasks.length === 0) {
            container.innerHTML = `
                <div class="empty-state">
                    <i class="fas fa-inbox"></i>
                    <p>No tasks created yet</p>
                </div>
            `;
            return;
        }

        container.innerHTML = tasks.map(task => `
  <div class="task-item">
    <div class="task-header">
      <div class="task-title">${escapeHtml(task.title)}</div>

      <span class="task-status status-${task.status}">
        ${formatStatus(task.status)}
      </span>
    </div>

    <div class="task-description">
      ${escapeHtml(task.description)}
    </div>

    <div class="task-actions">
      <button class="btn-edit" onclick="startEditTask(${task.id})" title="Edit">
        Edit
      </button>

      <button class="btn-delete" onclick="deleteTask(${task.id})" title="Delete">
        Delete
      </button>
    </div>
  </div>
`).join('');

    } catch (error) {
        console.error('Error fetching tasks:', error);
        container.innerHTML = `
            <div class="empty-state">
                <i class="fas fa-exclamation-triangle"></i>
                <p>Unable to connect to backend service</p>
            </div>
        `;
    }
}

// Create a new task
async function createTask(taskData) {
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(taskData)
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to create task');
        }

        return await response.json();
    } catch (error) {
        throw error;
    }
}

// Show message
function showMessage(message, type) {
    const messageEl = document.getElementById('formMessage');
    messageEl.textContent = message;
    messageEl.className = `message show ${type}`;

    setTimeout(() => {
        messageEl.className = 'message';
    }, 5000);
}

// Format status for display
function formatStatus(status) {
    return status.replace('_', ' ');
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Handle form submission
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('taskForm');

    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            const payload = {
                title: document.getElementById('title').value.trim(),
                description: document.getElementById('description').value.trim(),
                status: document.getElementById('status').value
            };

            try {
                if (editingTaskId === null) {
                    // CREATE
                    await createTask(payload);
                    showMessage('Task created successfully', 'success');
                } else {
                    // UPDATE
                    await updateTask(editingTaskId, payload);
                    showMessage('Task updated successfully', 'success');
                    editingTaskId = null;

                    document.querySelector('#taskForm button[type="submit"]').innerText =
                        'Create Task';
                }

                e.target.reset();
                fetchTasks();

            } catch (error) {
                showMessage(error.message || 'Operation failed', 'error');
            }
        });
    }

    // Load tasks on page load
    fetchTasks();
});

async function deleteTask(id) {
    const confirmed = confirm('Are you sure you want to delete this task?');
    if (!confirmed) return;

    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete task');
        }

        if (editingTaskId === id) {
            editingTaskId = null;
            document.getElementById('taskForm').reset();
            document.querySelector('#taskForm button[type="submit"]').innerText =
                'Create Task';
        }

        fetchTasks();
    } catch (error) {
        alert(error.message);
    }
}

async function startEditTask(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        if (!response.ok) {
            throw new Error('Failed to fetch task');
        }

        const task = await response.json();

        editingTaskId = id;

        document.getElementById('title').value = task.title;
        document.getElementById('description').value = task.description;
        document.getElementById('status').value = task.status;

        document.querySelector('#taskForm button[type="submit"]').innerText =
            'Update Task';

    } catch (error) {
        editingTaskId = null;
        alert(error.message);
    }
}
async function updateTask(id, taskData) {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(taskData)
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Failed to update task');
    }

    return response.json();
}