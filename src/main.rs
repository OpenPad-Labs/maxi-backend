use std::net::SocketAddr;

use axum::{
    routing::{get, post},
    Router, Server, response::Html
};
use maxi_backend::handler;

#[tokio::main]
async fn main() {
    let app = Router::new()
        .route("/", get(index_handler))
        .route("/todos", get(handler::todo::todos_handler).post(handler::todo::create_todo_handler))
        .route("/login", post(handler::login::login_handler));

    let addr = SocketAddr::from(([127, 0, 0, 1], 8080));

    println!("Listening on http://{}", addr);

    Server::bind(&addr)
        .serve(app.into_make_service())
        .await
        .unwrap();
}

pub async fn index_handler() -> Html<&'static str> {
    Html("Hello Maxi")
}

