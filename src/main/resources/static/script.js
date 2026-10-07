const API_BASE = "/api";

let allClaims = [];

// Load claims when application opens
document.addEventListener("DOMContentLoaded", function () {
  loadClaims();

  document.getElementById("claimForm").addEventListener("submit", submitClaim);
});

// ===============================
// Load Claims
// ===============================

async function loadClaims() {
  try {
    const response = await fetch(`${API_BASE}/claims`);

    if (!response.ok) {
      throw new Error("Failed to load claims");
    }

    allClaims = await response.json();

    updateDashboard();
    displayClaims(allClaims);
    displayRecentClaims(allClaims);
  } catch (error) {
    console.error(error);

    document.getElementById("claimsTableContainer").innerHTML =
      `<div class="empty-message">
                Unable to load claims. Please check the server.
            </div>`;
  }
}

// ===============================
// Dashboard
// ===============================

function updateDashboard() {
  const total = allClaims.length;

  const pending = allClaims.filter(
    (claim) => claim.status === "PENDING",
  ).length;

  const approved = allClaims.filter(
    (claim) => claim.status === "APPROVED",
  ).length;

  const rejected = allClaims.filter(
    (claim) => claim.status === "REJECTED",
  ).length;

  document.getElementById("totalClaims").textContent = total;
  document.getElementById("pendingClaims").textContent = pending;
  document.getElementById("approvedClaims").textContent = approved;
  document.getElementById("rejectedClaims").textContent = rejected;
}

// ===============================
// Display Claims
// ===============================
function displayClaims(claims) {
  const container = document.getElementById("claimsTableContainer");

  if (claims.length === 0) {
    container.innerHTML = `<div class="empty-message">
                No claims found.
            </div>`;

    return;
  }

  let html = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Claimant</th>
                    <th>Date</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>
    `;

  claims.forEach((claim) => {
    html += `
            <tr>

                <td>${claim.id}</td>

                <td>${escapeHtml(claim.claimant)}</td>

                <td>${claim.date}</td>

                <td>${escapeHtml(claim.category)}</td>

                <td>
                    ${escapeHtml(claim.currency)}
                    ${Number(claim.amount).toFixed(2)}
                </td>

                <td>
                    <span class="status status-${claim.status}">
                        ${claim.status}
                    </span>
                </td>

                <td>

                    <button
                        class="action-button"
                        onclick="reviewClaimWithAI(${claim.id})">
                        🤖 AI Review
                    </button>

                    ${
                      claim.status === "PENDING"
                        ? `
                        <button
                            class="action-button approve-button"
                            onclick="approveClaim(${claim.id})">
                            Approve
                        </button>

                        <button
                            class="action-button reject-button"
                            onclick="rejectClaim(${claim.id})">
                            Reject
                        </button>
                        `
                        : ""
                    }

                    <button
                        class="action-button delete-button"
                        onclick="deleteClaim(${claim.id})">
                        Delete
                    </button>

                </td>

            </tr>
        `;
  });

  html += `
            </tbody>
        </table>
    `;

  container.innerHTML = html;
}
// ===============================
// Recent Claims
// ===============================

function displayRecentClaims(claims) {
  const container = document.getElementById("recentClaims");

  if (claims.length === 0) {
    container.innerHTML = `<div class="empty-message">
                No claims available.
            </div>`;

    return;
  }

  const recent = claims.slice(-5).reverse();

  let html = `
        <table>

            <thead>
                <tr>
                    <th>Claimant</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Status</th>
                </tr>
            </thead>

            <tbody>
    `;

  recent.forEach((claim) => {
    html += `
            <tr>

                <td>${escapeHtml(claim.claimant)}</td>

                <td>${escapeHtml(claim.category)}</td>

                <td>
                    ${escapeHtml(claim.currency)}
                    ${Number(claim.amount).toFixed(2)}
                </td>

                <td>
                    <span class="status status-${claim.status}">
                        ${claim.status}
                    </span>
                </td>

            </tr>
        `;
  });

  html += `
            </tbody>
        </table>
    `;

  container.innerHTML = html;
}

// ===============================
// Add Claim
// ===============================

async function submitClaim(event) {
  event.preventDefault();

  const message = document.getElementById("formMessage");

  message.textContent = "";
  message.className = "form-message";

  const claim = {
    claimant: document.getElementById("claimant").value.trim(),

    date: document.getElementById("date").value,

    category: document.getElementById("category").value,

    amount: Number(document.getElementById("amount").value),

    currency: document.getElementById("currency").value,

    description: document.getElementById("description").value.trim(),

    receiptAvailable:
      document.getElementById("receiptAvailable").value === "true",
  };

  try {
    const response = await fetch(`${API_BASE}/claims`, {
      method: "POST",

      headers: {
        "Content-Type": "application/json",
      },

      body: JSON.stringify(claim),
    });

    if (!response.ok) {
      const errorText = await response.text();

      throw new Error(errorText || "Unable to create claim");
    }

    message.textContent = "Claim submitted successfully.";
    message.classList.add("success-message");

    document.getElementById("claimForm").reset();

    await loadClaims();
  } catch (error) {
    console.error(error);

    message.textContent =
      "Unable to submit claim. Please check the entered details.";

    message.classList.add("error-message");
  }
}

// ===============================
// Approve Claim
// ===============================

async function approveClaim(id) {
  if (!confirm("Are you sure you want to approve this claim?")) {
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/claims/${id}/approve`, {
      method: "PUT",
    });

    if (!response.ok) {
      throw new Error("Approval failed");
    }

    await loadClaims();
  } catch (error) {
    console.error(error);

    alert("Unable to approve claim.");
  }
}

// ===============================
// Reject Claim
// ===============================

async function rejectClaim(id) {
  if (!confirm("Are you sure you want to reject this claim?")) {
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/claims/${id}/reject`, {
      method: "PUT",
    });

    if (!response.ok) {
      throw new Error("Rejection failed");
    }

    await loadClaims();
  } catch (error) {
    console.error(error);

    alert("Unable to reject claim.");
  }
}

// ===============================
// Delete Claim
// ===============================

async function deleteClaim(id) {
  if (!confirm("Are you sure you want to delete this claim?")) {
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/claims/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("Delete failed");
    }

    await loadClaims();
  } catch (error) {
    console.error(error);

    alert("Unable to delete claim.");
  }
}

// ===============================
// Search & Filter
// ===============================

function filterClaims() {
  const search = document.getElementById("searchInput").value.toLowerCase();

  const status = document.getElementById("statusFilter").value;

  const filtered = allClaims.filter((claim) => {
    const matchesSearch = claim.claimant.toLowerCase().includes(search);

    const matchesStatus = status === "" || claim.status === status;

    return matchesSearch && matchesStatus;
  });

  displayClaims(filtered);
}

// ===============================
// Navigation
// ===============================

function showSection(sectionId) {
  const sections = document.querySelectorAll(".section");

  sections.forEach((section) => {
    section.classList.add("hidden");
  });

  document.getElementById(sectionId).classList.remove("hidden");

  const titles = {
    dashboard: "Dashboard",

    claims: "Claims",

    addClaim: "Add Expense Claim",
  };

  document.getElementById("pageTitle").textContent = titles[sectionId];

  const navItems = document.querySelectorAll(".nav-item");

  navItems.forEach((item) => {
    item.classList.remove("active");
  });

  if (sectionId === "dashboard") {
    navItems[0].classList.add("active");
  }

  if (sectionId === "claims") {
    navItems[1].classList.add("active");
  }

  if (sectionId === "addClaim") {
    navItems[2].classList.add("active");
  }
}

// ===============================
// Security helper
// ===============================

function escapeHtml(value) {
  if (value === null || value === undefined) {
    return "";
  }

  return String(value)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}

async function reviewClaimWithAI(claimId) {
  try {
    const response = await fetch(`/api/claims/${claimId}`);

    if (!response.ok) {
      throw new Error("Could not load claim");
    }

    const claim = await response.json();

    const aiResponse = await fetch("/api/ai/review", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        claimant: claim.claimant,
        category: claim.category,
        amount: String(claim.amount),
        currency: claim.currency,
        description: claim.description || "",
        receiptAvailable: claim.receiptAvailable,
      }),
    });

    if (!aiResponse.ok) {
      throw new Error("AI review failed");
    }

    const result = await aiResponse.text();

    alert("AI Review Result:\n\n" + result);
  } catch (error) {
    alert("AI Review failed: " + error.message);
  }
}
