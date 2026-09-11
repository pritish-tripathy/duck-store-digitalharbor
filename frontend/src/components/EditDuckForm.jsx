import { useState } from "react";
import { updateDuck } from "../services/duckService";

function EditDuckForm({ duck, onDuckUpdated, onCancel }) {

    const [price, setPrice] = useState(duck.price);
    const [quantity, setQuantity] = useState(duck.quantity);

    const [error, setError] = useState("");
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {

        event.preventDefault();

        try {

            setError("");
            setSaving(true);

            await updateDuck(duck.id, {
                price: Number(price),
                quantity: Number(quantity)
            });

            await onDuckUpdated();

        } catch (error) {

            setError(error.message);

        } finally {

            setSaving(false);
        }
    }

    return (
        <form onSubmit={handleSubmit}>

            <div className="form-grid">

                <div className="form-field">

                    <label>
                        Color
                    </label>

                    <input
                        value={duck.color}
                        disabled
                    />

                </div>


                <div className="form-field">

                    <label>
                        Size
                    </label>

                    <input
                        value={duck.size}
                        disabled
                    />

                </div>


                <div className="form-field">

                    <label>
                        Price (USD)
                    </label>

                    <input
                        type="number"
                        step="0.01"
                        min="0.01"
                        value={price}
                        onChange={(event) =>
                            setPrice(event.target.value)
                        }
                        required
                    />

                </div>


                <div className="form-field">

                    <label>
                        Quantity
                    </label>

                    <input
                        type="number"
                        min="0"
                        value={quantity}
                        onChange={(event) =>
                            setQuantity(event.target.value)
                        }
                        required
                    />

                </div>

            </div>


            <div className="form-actions">

                <button
                    type="submit"
                    className="primary-button"
                    disabled={saving}
                >
                    {saving ? "Saving..." : "Save Changes"}
                </button>

                <button
                    type="button"
                    className="secondary-button"
                    onClick={onCancel}
                    disabled={saving}
                >
                    Cancel
                </button>

            </div>


            {error && (
                <div className="alert error">
                    <span>⚠</span>
                    {error}
                </div>
            )}

        </form>
    );
}

export default EditDuckForm;