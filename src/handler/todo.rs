
use axum::{
    http::StatusCode,
    Json
};

use crate::model::{todo::{Todo, CreateTodoCmd}, login::Claims};

// handlers
pub async fn todos_handler() -> Json<Vec<Todo>> {
    Json(vec![
        Todo {
            id: 1,
            title: "James King".to_string(),
            completed: false,
        },
        Todo {
            id: 2,
            title: "Durant".to_string(),
            completed: false,
        },
    ])
}

pub async fn create_todo_handler(claims: Claims, Json(_json): Json<CreateTodoCmd>) -> StatusCode {
    let _id = claims.id;
    StatusCode::ACCEPTED
}