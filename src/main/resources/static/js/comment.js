function editComment(button) {
    const commentId = button.getAttribute('data-id');
    const boardId = button.getAttribute('data-board-id');
    const commentElement = document.getElementById('comment-' + commentId);
    const commentContent = commentElement.textContent;

    const newContent = prompt('Edit your comment:', commentContent);
    if (newContent) {
        $.post(`/board/${boardId}/comment/${commentId}/update`, {content: newContent}, function(data) {
            window.location.href = data;
        });
    }
}