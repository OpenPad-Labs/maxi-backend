use serde::{Deserialize, Serialize};

/// struts
#[derive(Debug, Deserialize, Serialize)]
pub struct Todo {
    pub id: usize,
    pub title: String,
    pub completed: bool,
}

#[derive(Debug, Deserialize, Serialize)]
pub struct CreateTodoCmd {
    pub title: String,
}
