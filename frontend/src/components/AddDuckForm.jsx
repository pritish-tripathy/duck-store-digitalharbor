import { useState } from "react";
import { addDuck } from "../services/duckService";

function AddDuckForm({ onDuckAdded }) {

    const [color, setColor] = useState("RED");
    const [size, setSize] = useState("MEDIUM");
    const [price, setPrice] = useState("");
    const [quantity, setQuantity] = useState("");

    const [error, setError] = useState("");
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {

        event.preventDefault();

        try {

            setError("");
            setSaving(true);

            await addDuck({
                color: color,
                size: size,
                price: Number(price),
                quantity: Number(quantity)
            });

            setPrice("");
            setQuantity("");

            await onDuckAdded();

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

                    <select
                        value={color}
                        onChange={(event) =>
                            setColor(event.target.value)
                        }
                    >
                        <option value="RED">Red</option>
                        <option value="GREEN">Green</option>
                        <option value="YELLOW">Yellow</option>
                        <option value="BLACK">Black</option>
                    </select>

                </div>


                <div className="form-field">

                    <label>
                        Size
                    </label>

                    <select
                        value={size}
                        onChange={(event) =>
                            setSize(event.target.value)
                        }
                    >
                        <option value="XLARGE">XLarge</option>
                        <option value="LARGE">Large</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="SMALL">Small</option>
                        <option value="XSMALL">XSmall</option>
                    </select>

                </div>


                <div className="form-field">

                    <label>
                        Price (USD)
                    </label>

                    <input
                        type="number"
                        step="0.01"
                        min="0.01"
                        placeholder="e.g. 10.00"
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
                        min="1"
                        placeholder="e.g. 100"
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
                    {saving ? "Adding..." : "Add Duck"}
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

export default AddDuckForm;