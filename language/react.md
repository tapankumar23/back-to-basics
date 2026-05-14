# Basic React Interview Questions

## Core Concepts

1. What is React, and why is it used for building UI applications?
2. What is the difference between a library and a framework, and where does React fit?
3. What is JSX?
4. How does React update the DOM efficiently?
5. What is the Virtual DOM?
6. What are components in React?
7. What is the difference between functional components and class components?
8. What are props in React?
9. What is state in React?
10. What is the difference between props and state?

## Rendering And Lifecycle

1. What triggers a re-render in React?
2. What is the React component lifecycle at a high level?
3. What is the use of `useEffect()`?
4. What is the difference between `useEffect()` and `useLayoutEffect()`?
5. Why can unnecessary re-renders become a performance issue?
6. What is conditional rendering in React?
7. How do you render a list in React?
8. Why is the `key` prop important when rendering lists?

## Hooks

1. What problem do Hooks solve in React?
2. What does `useState()` do?
3. What does `useEffect()` do?
4. What is `useRef()` used for?
5. What is `useMemo()` used for?
6. What is `useCallback()` used for?
7. What is `useContext()` used for?
8. What are the rules of Hooks?
9. Why should Hooks not be called conditionally?

## Forms And Events

1. How are events handled in React?
2. What is the difference between controlled and uncontrolled components?
3. How do you handle form input in React?
4. How do you lift state up between components?
5. How do parent and child components communicate in React?

## State Management

1. When is local component state enough?
2. When would you use Context API?
3. What are the drawbacks of using Context for everything?
4. When might you choose Redux, Zustand, or another state management library?
5. What is prop drilling?
6. How can prop drilling be reduced?

## Performance And Optimization

1. What is memoization in React?
2. What does `React.memo()` do?
3. When should `useMemo()` and `useCallback()` be avoided?
4. How would you identify performance bottlenecks in a React app?
5. What are common causes of unnecessary renders?
6. What is code splitting in React?
7. What is lazy loading in React?

## Architecture And Best Practices

1. How do you structure a medium-sized React application?
2. How do you separate presentation and business logic in React?
3. What makes a React component reusable?
4. What are common anti-patterns in React?
5. How do you manage shared UI state versus server state?
6. What are custom hooks, and when should you create one?

## Testing

1. How do you test a React component?
2. What is the difference between unit testing and integration testing in React apps?
3. What tools are commonly used for testing React applications?
4. What should be tested in a form-heavy React component?
5. Why is testing behavior usually preferred over testing implementation details?

## Scenario-Based Questions

1. How would you design a reusable table component in React?
2. How would you avoid unnecessary renders in a dashboard with many widgets?
3. How would you share authentication state across the application?
4. How would you handle API loading, success, and error states in a clean way?
5. How would you refactor a very large React component that has too many responsibilities?

## Good Follow-Up Questions For Senior Candidates

1. How do you decide component boundaries in a React codebase?
2. How do you balance readability versus optimization in React?
3. How do you introduce standards for hooks, state, and folder structure across teams?
4. What React mistakes do you see engineers make most often?
5. How do you review React code for maintainability?
