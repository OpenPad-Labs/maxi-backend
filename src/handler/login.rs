
use axum::{
    Json
};

use jsonwebtoken as jwt;

use crate::{model::login::{Claims, LoginRequest, LoginResponse}, SECRET};

use super::get_epoch;


pub async fn login_handler(Json(_login): Json<LoginRequest>) -> Json<LoginResponse> {
    // TODO validate the login info
    let claims = Claims {
        id: 1,
        name: "JamesBond".to_string(),
        exp: get_epoch() + 14 * 24 * 60 * 60,
    };

    let key = jwt::EncodingKey::from_secret(SECRET);
    let token = jwt::encode(&jwt::Header::default(), &claims, &key).unwrap();
    Json(LoginResponse { token })
}