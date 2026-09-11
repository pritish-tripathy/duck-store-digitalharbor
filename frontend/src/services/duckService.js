const API_URL = "http://localhost:8080/api/ducks";

export async function getDucks() {
    const response = await fetch(API_URL);

    if (!response.ok) {
        throw new Error("Failed to fetch ducks");
    }

    return response.json();
}

export async function addDuck(duck) {
    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(duck)
    });

    if (!response.ok) {
        throw new Error("Failed to add duck");
    }
}

export async function updateDuck(id, duck) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(duck)
    });

    if (!response.ok) {
        throw new Error("Failed to update duck");
    }
}

export async function deleteDuck(id) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    if (!response.ok) {
        throw new Error("Failed to delete duck");
    }
}
