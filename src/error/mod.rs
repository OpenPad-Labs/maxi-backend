use axum::{http::StatusCode, response::IntoResponse};

#[derive(Debug)]
pub enum HttpError {
    Auth,
    Internal,
}

impl IntoResponse for HttpError {
    fn into_response(self) -> axum::response::Response {
        let (code, msg) = match self {
            HttpError::Auth => (StatusCode::UNAUTHORIZED, "Unauthorized"),
            HttpError::Internal => (StatusCode::INTERNAL_SERVER_ERROR, "Internal Server Error"),
        };

        (code, msg).into_response()
    }
}
