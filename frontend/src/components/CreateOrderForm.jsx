import { useState } from "react";
import { createOrder } from "../services/orderService";

function CreateOrderForm({ onOrderCreated }) {

    const [color, setColor] = useState("RED");
    const [size, setSize] = useState("MEDIUM");
    const [quantity, setQuantity] = useState("");
    const [destinationCountry, setDestinationCountry] = useState("India");
    const [shippingMode, setShippingMode] = useState("LAND");

    const [error, setError] = useState("");
    const [creating, setCreating] = useState(false);

    async function handleSubmit(event) {

        event.preventDefault();

        try {

            setError("");
            setCreating(true);

            const order = {
                color: color,
                size: size,
                quantity: Number(quantity),
                destinationCountry: destinationCountry,
                shippingMode: shippingMode
            };

            const response =
                await createOrder(order);

            setQuantity("");

            onOrderCreated(response);

        } catch (error) {

            setError(error.message);

        } finally {

            setCreating(false);
        }
    }

    return (
        <form onSubmit={handleSubmit}>

            <div className="form-grid">

                <div className="form-field">

                    <label>
                        Duck Color
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
                        Duck Size
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
                        Quantity
                    </label>

                    <input
                        type="number"
                        min="1"
                        placeholder="Number of ducks"
                        value={quantity}
                        onChange={(event) =>
                            setQuantity(event.target.value)
                        }
                        required
                    />

                </div>


                <div className="form-field">

                    <label>
                        Destination
                    </label>

                    <select
                        value={destinationCountry}
                        onChange={(event) =>
                            setDestinationCountry(event.target.value)
                        }
                    >
                        <option value="USA">USA</option>
                        <option value="Bolivia">Bolivia</option>
                        <option value="India">India</option>
                        <option value="Other">Other</option>
                    </select>

                </div>


                <div className="form-field">

                    <label>
                        Shipping Mode
                    </label>

                    <select
                        value={shippingMode}
                        onChange={(event) =>
                            setShippingMode(event.target.value)
                        }
                    >
                        <option value="LAND">Land</option>
                        <option value="AIR">Air</option>
                        <option value="SEA">Sea</option>
                    </select>

                </div>

            </div>


            <div className="form-actions">

                <button
                    type="submit"
                    className="primary-button"
                    disabled={creating}
                >
                    {creating
                        ? "Calculating..."
                        : "Calculate Order Price"}
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

export default CreateOrderForm;