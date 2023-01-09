
use axum::{
    async_trait,
    extract::{FromRequest, RequestParts},
    headers::{authorization::Bearer, Authorization},
    TypedHeader,
};
use jsonwebtoken as jwt;
use jwt::Validation;
use serde::{Deserialize, Serialize};

use crate::{error::HttpError, SECRET};

#[derive(Debug, Serialize, Deserialize)]
pub struct Claims {
    pub id: usize,
    pub name: String,
    pub exp: usize,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct LoginRequest {
    pub email: String,
    pub password: String,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct LoginResponse {
    pub token: String,
}

#[async_trait]
impl<B> FromRequest<B> for Claims
where
    B: Send, // required by `async_trait`
{
    type Rejection = HttpError;

    async fn from_request(req: &mut RequestParts<B>) -> Result<Self, Self::Rejection> {
        let TypedHeader(Authorization(bearer)) =
            TypedHeader::<Authorization<Bearer>>::from_request(req)
                .await
                .map_err(|_| HttpError::Auth)?;
        let key = jwt::DecodingKey::from_secret(SECRET);
        let token = jwt::decode::<Claims>(bearer.token(), &key, &Validation::default())
            .map_err(|_| HttpError::Auth)?;

        Ok(token.claims)
    }
}
