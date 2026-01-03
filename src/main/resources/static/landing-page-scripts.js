const API_BASE_URL = '/api/tasks';

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

            const formData = {
                title: document.getElementById('title').value.trim(),
                description: document.getElementById('description').value.trim(),
                status: document.getElementById('status').value
            };

            try {
                await createTask(formData);
                showMessage('Task created successfully', 'success');

                // Reset form
                e.target.reset();

                // Refresh task list
                fetchTasks();
            } catch (error) {
                showMessage(error.message || 'Failed to create task', 'error');
            }
        });
    }

    // Load tasks on page load
    fetchTasks();
});