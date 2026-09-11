const API_URL = "http://localhost:8080/api/orders";

export async function createOrder(order) {

    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(order)
    });

    if (!response.ok) {
        throw new Error("Failed to create order");
    }

    return response.json();
}